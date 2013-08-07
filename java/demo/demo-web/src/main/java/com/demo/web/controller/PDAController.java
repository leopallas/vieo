package com.demo.web.controller;

import com.demo.constants.ServerErrorConstants;
import com.demo.util.FileUploadHelper;
import com.demo.vo.HeartbeatVO;
import com.demo.vo.LocationVO;
import com.demo.vo.LoginResultVO;
import com.demo.vo.LoginVO;
import com.demo.vo.PictureVO;
import com.demo.vo.RegisterVO;
import com.demo.vo.UserVO;
import com.demo.web.domain.User;
import com.demo.web.exception.PDAException;
import com.demo.web.service.PDAService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: xw Date: 4/4/13 Time: 1:51 PM To change
 * this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("pda")
public class PDAController {
    private static final String TAG = PDAController.class.getName();

    @Autowired
    private PDAService          pdaService;

    @Autowired
    private File                fileRootPath;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public void register(@RequestBody RegisterVO registerVO) {
        pdaService.register(registerVO);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResultVO login(@RequestBody LoginVO loginVO) {
        if (StringUtils.isEmpty(loginVO.getUsrId())) {
            throw new PDAException(ServerErrorConstants.USER_NOT_EMPTY);
        }
        if (StringUtils.isEmpty(loginVO.getPwd())) {
            throw new PDAException(ServerErrorConstants.USER_PWD_NOT_EMPTY);
        }
        if (StringUtils.isEmpty(loginVO.getSerialNo())) {
            throw new PDAException(ServerErrorConstants.DEVICE_NO_NOT_EMPTY);
        }

        return pdaService.login(loginVO);
    }

    @RequestMapping(value = "get-users", method = RequestMethod.GET)
    @ResponseBody
    public UserVO[] getUsers() {
        List<User> userList = pdaService.getUserList();
        if(CollectionUtils.isEmpty(userList)) {
            return null;
        }

        UserVO[] userVOs = new UserVO[userList.size()];
        int index = 0;
        for (User user : userList) {
            userVOs[index] = new UserVO();
            BeanUtils.copyProperties(user, userVOs[index]);
            index++;
        }
        return userVOs;
    }

    @RequestMapping(value = "sync/logout", method = RequestMethod.GET)
    @ResponseBody
    public void logout(@RequestParam(value = "tkn") String secretToken) {
        pdaService.logout(secretToken);
    }

    @RequestMapping(value = "sync/upload-picture", method = RequestMethod.POST)
    @ResponseBody
    public PictureVO uploadPicture(@RequestPart(value = "json-data", required = false) PictureVO pictureVO,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        if (pictureVO == null || StringUtils.isEmpty(pictureVO.getPicId())
                || StringUtils.isEmpty(pictureVO.getUserId())
                || StringUtils.isEmpty(pictureVO.getPicName())
                || pictureVO.getPicType() == null
                || pictureVO.getCreateDt() == null) {
            throw new PDAException(ServerErrorConstants.INVALID_REQUEST_PARAMETER);
        }

        if (file == null || file.isEmpty()) {
            throw new PDAException(ServerErrorConstants.INVALID_UPLOAD_FILE);
        }

        String fileExtendType = FileUploadHelper.getExtensionType(file.getOriginalFilename());
        if (StringUtils.isEmpty(fileExtendType)) {
            throw new PDAException(ServerErrorConstants.INVALID_REQUEST_PARAMETER);
        }

        String usrId = pictureVO.getUserId();
//        pictureVO.setPicId(IdGeneratorHelper.getID());
        pictureVO.setPicType(pictureVO.getPicType() + 1);
        try {
            // save file to disk
            if(".zip".equals(fileExtendType)) {
                FileUploadHelper.persistentUpdateZipFile(this.fileRootPath, usrId, pictureVO.getPicId(),
                        file.getOriginalFilename(), file.getInputStream());
            } else {
                FileUploadHelper.persistentUpdateFile(this.fileRootPath, usrId, pictureVO.getPicId(),
                        fileExtendType, file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new PDAException(ServerErrorConstants.UPLOAD_FILE_PERSISTENT_FAIL, e.getCause());
        }

        return pdaService.uploadPicture(pictureVO);
    }

    @RequestMapping(value = "sync/upload-picture-flag", method = RequestMethod.POST)
    @ResponseBody
    public PictureVO updatePictureFlag(@RequestBody PictureVO pictureVO) {
        if (pictureVO == null || StringUtils.isEmpty(pictureVO.getPicId())
                || StringUtils.isEmpty(pictureVO.getClh())
                || pictureVO.getXc() == null
                || pictureVO.getTw() == null
                || pictureVO.getBw() == null
                || pictureVO.getGzfj() == null
                || pictureVO.getGzfl() == null) {
            throw new PDAException(ServerErrorConstants.INVALID_REQUEST_PARAMETER);
        }

        return pdaService.updatePictureFlag(pictureVO);
    }

    @RequestMapping(value = "sync/heartbeat", method = RequestMethod.POST)
    @ResponseBody
    public HeartbeatVO heartbeat(@RequestBody LocationVO locationVO,
                                 @RequestParam(value = "timeline") long timeline,
            HttpServletRequest request) {

        String usrId = (String) request.getAttribute("usrId");
        return pdaService.heartbeat(usrId, timeline, locationVO);
    }

    @RequestMapping(value = "sync/get-sync-pictures", method = RequestMethod.GET)
    @ResponseBody
    public PictureVO[] getSyncPictures(@RequestParam(value = "timeline", required = false) Long timeline,
            HttpServletRequest request) {
        String usrId = (String) request.getAttribute("usrId");
        return pdaService.getSyncPictures(timeline, usrId);
    }
}
