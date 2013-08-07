package com.demo.web.service.impl;

import com.demo.constants.ServerErrorConstants;
import com.demo.db.Indicator;
import com.demo.util.IdGeneratorHelper;
import com.demo.util.security.AuthenticationKey;
import com.demo.vo.HeartbeatVO;
import com.demo.vo.LocationVO;
import com.demo.vo.LoginResultVO;
import com.demo.vo.LoginVO;
import com.demo.vo.PictureVO;
import com.demo.vo.RegisterVO;
import com.demo.web.dao.DeviceMapper;
import com.demo.web.dao.LocationMapper;
import com.demo.web.dao.PictureMapper;
import com.demo.web.dao.UserMapper;
import com.demo.web.domain.Device;
import com.demo.web.domain.Location;
import com.demo.web.domain.Picture;
import com.demo.web.domain.User;
import com.demo.web.exception.PDAException;
import com.demo.web.service.PDAService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PDAServiceImpl implements PDAService {

    @Autowired
    private DeviceMapper   deviceMapper;

    @Autowired
    private UserMapper     userMapper;

    @Autowired
    private PictureMapper  pictureMapper;

    @Autowired
    private LocationMapper locationMapper;

    private double defaultLat = 100D;
    private double defaultLon = 20D;

    @Override
    public User getUser(String secretToken) {
        return userMapper.selectBySecretToken(secretToken);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.selectAll();
    }

    @Transactional
    @Override
    public LoginResultVO login(LoginVO login) {
        // 检查登录设备是否注册
        Device device = deviceMapper.selectBySerialNo(login.getSerialNo());
        if (device == null) {
            throw new PDAException(ServerErrorConstants.INVALID_DEVICE);
        }

        // 检查登录用户名是否注册
        User user = userMapper.selectByPrimaryKey(login.getUsrId());
        if (user == null) {
            throw new PDAException(ServerErrorConstants.INVALID_USER);
        }
        // 检查登录用户是否已经在其它设备登录
        Device device1 = deviceMapper.selectByUsrId(login.getUsrId());
        if (device1 != null) {
            if (device1.getSerialNo().equals(login.getSerialNo())) {
                // throw new
                // PDAException(ServerErrorConstants.USER_ALREADY_LOGIN);
            } else {
                throw new PDAException(ServerErrorConstants.USER_LOGINED_ON_OTHER_DEVICE);
            }
        }
        // 检查登录用户密码
        if (StringUtils.isEmpty(user.getUsrPwd()) || !user.getUsrPwd().equals(login.getPwd())) {
            throw new PDAException(ServerErrorConstants.INVALID_PASSWORD);
        }

        // 生成Secret token
        AuthenticationKey key = AuthenticationKey.generateAuthenticationKey();
        user.setSecretToken(key.getSecretToken());
        user.setSignatureKey(key.getSignatureKey());
        userMapper.updateByPrimaryKeySelective(user);

        Date systemDate = new Date();
        device.setStatus(Indicator.LOGIN_STATUS_ONLINE.getCode());
        device.setUsrId(user.getUsrId());
        device.setLoginDt(systemDate);
        device.setUpdateDt(systemDate);
        deviceMapper.updateByBarCode(device);
        return new LoginResultVO(key.getSecretToken(), key.getSignatureKey(), user.getUsrName(), user.getUsrType(),
                System.currentTimeMillis());
    }

    @Transactional
    @Override
    public boolean register(RegisterVO registerVO) {
//        Device device = deviceMapper.selectBySerialNo(registerVO.getSerialNO());
//        //检查重复注册
//        if (device != null) {
//            throw new PDAException(ServerErrorConstants.DEVICE_ALREADY_REGISTER);
//        }

        // 检查条码是否有效
        Device device = deviceMapper.selectByBarCode(registerVO.getBarcode());
        if (device == null) {
            throw new PDAException(ServerErrorConstants.INVALID_REGISTER_BARCODE);
        }

        // 检查条码是否已经绑定设备
//        if (!StringUtils.isEmpty(device.getSerialNo())) {
//            throw new PDAException(ServerErrorConstants.REGISTER_BARCODE_USED);
//        }

        // 绑定设备学列号与条码
        device.setSerialNo(registerVO.getSerialNO());
        device.setBarCode(registerVO.getBarcode());
        deviceMapper.updateByBarCode(device);
        return true;
    }

    @Override
    public void logout(String secretToken) {
        User user = userMapper.selectBySecretToken(secretToken);

        // 清除登录token
        user.setSignatureKey(null);
        user.setSecretToken(null);
        userMapper.updateByPrimaryKey(user);

        // 清除登录数据
        Device device = deviceMapper.selectByUsrId(user.getUsrId());
        device.setStatus(null);
        device.setUsrId(null);
        device.setLoginDt(null);
        device.setUpdateDt(null);
        deviceMapper.updateByBarCode(device);
    }

    @Override
    public HeartbeatVO heartbeat(String usrId, long timeline, LocationVO locationVO) {
        Date systemDate = new Date();

        // 更新device表状态与时间
        Device device = deviceMapper.selectByUsrId(usrId);
        device.setStatus(Indicator.LOGIN_STATUS_ONLINE.getCode());
        device.setUpdateDt(systemDate);
        deviceMapper.updateByBarCode(device);

        // 更新坐标
        if (locationVO != null) {
            Location location = new Location();
            location.setLocId(IdGeneratorHelper.getID());
            location.setUsrId(usrId);
            location.setSerialNo(device.getSerialNo());
            location.setSrcType(locationVO.getSource());
            location.setLat(locationVO.getLat());
            location.setLon(locationVO.getLon());
            location.setCreateDt(systemDate);
            locationMapper.insert(location);
        }

        boolean isUpdatePicture = false;
        // 检查是否有新图片上传
        if (pictureMapper.getNumberOfPictureAfterTimeline(new Date(timeline), systemDate) > 0) {
            isUpdatePicture = true;
        }
        return new HeartbeatVO(isUpdatePicture);
    }

    @Transactional
    @Override
    public PictureVO uploadPicture(PictureVO pictureVO) {
        Date systemDate = new Date();
        Picture picture = new Picture();
        picture.setPicId(pictureVO.getPicId());
        picture.setUsrId(pictureVO.getUserId());
        picture.setPicType(pictureVO.getPicType());
        picture.setPicName(pictureVO.getPicName());
        picture.setPicDesc(pictureVO.getPicDesc());
        picture.setSodDesc(pictureVO.getSodDesc());
        if (pictureVO.getLat() == null || pictureVO.getLon() == null) {
//            Map<String, Double> gpsMap = pictureMapper.getGPSLocation(usrId);
//            if (gpsMap == null || gpsMap.isEmpty()) {
                double xx = (double)((int)(Math.random() * 1000000))/1000000;
                double yy = (double)((int)(Math.random() * 1000000))/1000000;
                picture.setLat(defaultLat + xx);
                picture.setLon(defaultLon + yy);
//            } else {
//                picture.setLat(gpsMap.get("lat"));
//                picture.setLon(gpsMap.get("lon"));
//            }
        } else {
            picture.setLat(pictureVO.getLat());
            picture.setLon(pictureVO.getLon());
        }
        picture.setCreateDt(new Date(pictureVO.getCreateDt()));
        picture.setUploadDt(systemDate);

        //added flag
        picture.setClh(pictureVO.getClh());
        picture.setXc(pictureVO.getXc());
        picture.setTw(pictureVO.getTw());
        picture.setBw(pictureVO.getBw());
        picture.setGzfj(pictureVO.getGzfj());
        picture.setGzfl(pictureVO.getGzfl());
        picture.setGznr(pictureVO.getGznr());

        try {
            pictureMapper.insertSelective(picture);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return fillPictureVO(picture, systemDate);
    }

    @Transactional
    @Override
    public PictureVO updatePictureFlag(PictureVO pictureVO) {
        Date systemDate = new Date();
        Picture picture = new Picture();
        picture.setPicId(pictureVO.getPicId());
        picture.setClh(pictureVO.getClh());
        picture.setXc(pictureVO.getXc());
        picture.setTw(pictureVO.getTw());
        picture.setBw(pictureVO.getBw());
        picture.setGzfj(pictureVO.getGzfj());
        picture.setGzfl(pictureVO.getGzfl());
        picture.setGznr(pictureVO.getGznr());
        try {
            pictureMapper.updateByPrimaryKeySelective(picture);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        picture = pictureMapper.selectByPrimaryKey(pictureVO.getPicId());
        return fillPictureVO(picture, systemDate);
    }

    @Override
    public PictureVO[] getSyncPictures(Long timeline, String usrId) {
        Date systemDate = new Date();

        List<Picture> pictureList;
        if (timeline == null) {
            pictureList = pictureMapper.selectPictureList();
        } else {
            pictureList = pictureMapper.selectPictureListAfterTimeline(new Date(timeline), systemDate, usrId);
        }

        if (CollectionUtils.isEmpty(pictureList)) {
            return null;
        }

        int index = 0;
        PictureVO[] pictureVOs = new PictureVO[pictureList.size()];
        for (Picture picture : pictureList) {
            pictureVOs[index++] = fillPictureVO(picture, systemDate);
        }
        return pictureVOs;
    }

    private PictureVO fillPictureVO(Picture picture, Date systemDate) {
        PictureVO pictureVO = new PictureVO();
        pictureVO.setPicId(picture.getPicId());
        pictureVO.setUserId(picture.getUsrId());
        pictureVO.setPicType(picture.getPicType() - 1);
        pictureVO.setPicName(picture.getPicName());
        pictureVO.setSodDesc(picture.getSodDesc());
        pictureVO.setCreateDt(picture.getCreateDt().getTime());
        pictureVO.setUploadDt(picture.getUploadDt().getTime());
        pictureVO.setUserName(picture.getUsrName());
        pictureVO.setTimeline(systemDate.getTime());
        pictureVO.setLat(picture.getLat());
        pictureVO.setLon(picture.getLon());

        pictureVO.setClh(picture.getClh());
        pictureVO.setXc(picture.getXc());
        pictureVO.setTw(picture.getTw());
        pictureVO.setBw(picture.getBw());
        pictureVO.setGzfj(picture.getGzfj());
        pictureVO.setGzfl(picture.getGzfl());
        pictureVO.setGznr(picture.getGznr());
        return pictureVO;
    }
}
