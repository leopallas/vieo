package com.demo.service.impl;

import com.demo.constants.MimeTypeConstants;
import com.demo.constants.ServerErrorConstants;
import com.demo.constants.URIConstants;
import com.demo.exception.BadRequestException;
import com.demo.service.PPMService;
import com.demo.service.ServerContext;
import com.demo.util.FileUploadHelper;
import com.demo.util.security.AuthenticationKey;
import com.demo.vo.HeartbeatVO;
import com.demo.vo.LocationVO;
import com.demo.vo.LoginResultVO;
import com.demo.vo.LoginVO;
import com.demo.vo.PictureVO;
import com.demo.vo.RegisterVO;
import com.demo.vo.ServerType;
import com.demo.vo.ServerVO;
import com.demo.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PPMServiceImpl implements PPMService {
    private ServerBridge serverBridge;

    private ServerVO     serverVO;

    private ObjectMapper objectMapper = new ObjectMapper();

    public PPMServiceImpl(String serverAddress, int serverPort) {
        serverBridge = new ServerBridge(serverAddress, serverPort, 0, false);
        serverVO = new ServerVO(serverAddress, serverPort, ServerType.ATTACHMENT,
                URIConstants.PPM_WEB_UPLOAD_CONTEXT_ROOT);
    }

    public PPMServiceImpl(String serverAddress, int serverPort, int secretServerPort, boolean https) {
        serverBridge = new ServerBridge(serverAddress, serverPort, secretServerPort, https);
        serverVO = new ServerVO(serverAddress, serverPort, ServerType.ATTACHMENT,
                URIConstants.PPM_WEB_UPLOAD_CONTEXT_ROOT);
    }

    @Override
    public void register(RegisterVO registerVO) throws BadRequestException {
        this.serverBridge.register(registerVO);
    }

    public LoginResultVO login(LoginVO loginVO) throws BadRequestException {
        return this.serverBridge.login(loginVO);
    }

    private boolean isLogin() {
        return this.serverBridge.isLogin();
    }

    public void setContext(ServerContext context) {
        this.serverBridge.setContext(context);
    }

    @Override
    public UserVO[] getUsers() throws BadRequestException {
        if (!isLogin()) {
            throw new BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
        }

        URL url;
        try {
            url = this.serverBridge.generateAuthenticationRequestURL(URIConstants.PDA_URI_REQUEST_USERS,
                    new String[][] {}, ServerBridge.METHOD_GET);
        } catch (MalformedURLException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }

        try {
            String result = this.serverBridge.doGet(url);
            if (StringUtils.isEmpty(result)) {
                return new UserVO[0];
            }
            return this.objectMapper.readValue(result, UserVO[].class);
        } catch (JsonGenerationException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (JsonMappingException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }
    }

    public boolean heartbeat(long timeline, LocationVO location) throws BadRequestException {
        if (!isLogin()) {
            throw new BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
        }

        URL url;
        try {
            url = this.serverBridge.generateAuthenticationRequestURL(URIConstants.PDA_URI_HEARTBEAT, new String[][] { {
                    "timeline", String.valueOf(timeline) } }, ServerBridge.METHOD_POST);
        } catch (MalformedURLException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }

        HeartbeatVO heartbeatVO;
        try {
            String entity = this.objectMapper.writeValueAsString(location);
            String result = this.serverBridge.doPost(url, entity, true);
            if (StringUtils.isEmpty(result)) {
                return false;
            }
            heartbeatVO = this.objectMapper.readValue(result, HeartbeatVO.class);
        } catch (JsonGenerationException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (JsonMappingException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }
        return heartbeatVO.isUpdatePicture();
    }

    @Override
    public PictureVO uploadPicture(PictureVO pictureVO, String zipFilePath) throws BadRequestException, IOException {
        if (!isLogin()) {
            throw new BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
        }

        URL url;
        try {
            url = this.serverBridge.generateAuthenticationRequestURL(URIConstants.PDA_URI_UPLOAD_PICTURE,
                    new String[][] {}, ServerBridge.METHOD_POST);
        } catch (MalformedURLException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }

        if (StringUtils.isEmpty(zipFilePath)) {
            throw new IOException(ServerErrorConstants.getDescription(ServerErrorConstants.UPLOAD_FILE_NOT_EXIST_ERROR));
        }

        File file = new File(zipFilePath);
        if (!file.exists()) {
            throw new IOException(ServerErrorConstants.getDescription(ServerErrorConstants.UPLOAD_FILE_NOT_EXIST_ERROR));
        }

        try {
            String entity = this.objectMapper.writeValueAsString(pictureVO);
            String result = this.serverBridge.doPostMultiForm(url, entity, file, MimeTypeConstants.APPLICATION_BIN);
            if (StringUtils.isEmpty(result)) {
                return new PictureVO();
            }
            return this.objectMapper.readValue(result, PictureVO.class);
        } catch (JsonGenerationException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (JsonMappingException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }
    }

    @Override
    public PictureVO uploadPictureFlag(PictureVO pictureVO) throws BadRequestException {
        if (!isLogin()) {
            throw new BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
        }

        URL url;
        try {
            url = this.serverBridge.generateAuthenticationRequestURL(URIConstants.PDA_URI_UPLOAD_PICTURE_FLAG,
                    new String[][] {}, ServerBridge.METHOD_POST);
        } catch (MalformedURLException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }

        try {
            String entity = this.objectMapper.writeValueAsString(pictureVO);
            String result = this.serverBridge.doPost(url, entity, true);
            if (StringUtils.isEmpty(result)) {
                return new PictureVO();
            }
            return this.objectMapper.readValue(result, PictureVO.class);
        } catch (JsonGenerationException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (JsonMappingException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }
    }

    public PictureVO[] getSyncPictures(Long timeline) throws BadRequestException {
        if (!isLogin()) {
            throw new BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
        }

        String[][] params = new String[][] {};
        if (timeline != null) {
            params = new String[][] { { "timeline", String.valueOf(timeline) } };
        }

        URL url;
        try {
            url = this.serverBridge.generateAuthenticationRequestURL(URIConstants.PDA_URI_REQUEST_SYNC_PICTURES,
                    params, ServerBridge.METHOD_GET);
        } catch (MalformedURLException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }

        try {
            String result = this.serverBridge.doGet(url);
            if (StringUtils.isEmpty(result)) {
                return new PictureVO[0];
            }
            return this.objectMapper.readValue(result, PictureVO[].class);
        } catch (JsonGenerationException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (JsonMappingException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(ServerBridge.CLIENT_GENERIC_ERROR, e, ServerBridge.PRINT_LOG);
        }
    }

    @Override
    public byte[] getPicture(PictureVO pictureVO) throws BadRequestException {
        if (!isLogin()) {
            throw new BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
        }

        URL url;
        try {
            String extensionType = FileUploadHelper.getExtensionType(pictureVO.getPicName());
            url = FileUploadHelper.getOriginalFileURI(serverVO, pictureVO.getUserId(), pictureVO.getPicId(),
                    pictureVO.getPicId() + extensionType);
            return this.serverBridge.doGetResource(url);
        } catch (MalformedURLException e) {
            throw new BadRequestException(e);
        }
    }

    public byte[] getSoundResource(PictureVO pictureVO) throws BadRequestException {
        if (!isLogin()) {
            throw new BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
        }

        URL url;
        try {
            if (StringUtils.isEmpty(pictureVO.getSodDesc())) {
                throw new BadRequestException("not sound");
            }
            String extensionType = FileUploadHelper.getExtensionType(pictureVO.getSodDesc());
            url = FileUploadHelper.getOriginalFileURI(serverVO, pictureVO.getUserId(), pictureVO.getPicId(),
                    pictureVO.getPicId() + extensionType);
            return this.serverBridge.doGetResource(url);
        } catch (MalformedURLException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public byte[] getThumbnailPicture(PictureVO pictureVO) throws BadRequestException {
        if (!isLogin()) {
            throw new BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
        }

        URL url;
        try {
            String extensionType = FileUploadHelper.getExtensionType(pictureVO.getPicName());
            url = FileUploadHelper.getThumbnailFileURI(serverVO, pictureVO.getUserId(), pictureVO.getPicId(),
                    pictureVO.getPicId() + extensionType);
            return this.serverBridge.doGetResource(url);
        } catch (MalformedURLException e) {
            throw new BadRequestException(e);
        }
    }

    public void logout() throws BadRequestException {
        this.serverBridge.logout();
    }
}
