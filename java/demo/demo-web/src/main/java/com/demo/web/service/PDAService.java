package com.demo.web.service;

import com.demo.vo.HeartbeatVO;
import com.demo.vo.LocationVO;
import com.demo.vo.LoginResultVO;
import com.demo.vo.LoginVO;
import com.demo.vo.PictureVO;
import com.demo.vo.RegisterVO;
import com.demo.web.domain.User;

import java.util.List;

public interface PDAService {

    public User getUser(String secretToken);

    public List<User> getUserList();

    public boolean register(RegisterVO registerVO);

    public LoginResultVO login(LoginVO login);

    public void logout(String secretToken);

    public HeartbeatVO heartbeat(String usrId, long timeline, LocationVO locationVO);

    public PictureVO uploadPicture(PictureVO pictureVO);

    public PictureVO updatePictureFlag(PictureVO pictureVO);

    public PictureVO[] getSyncPictures(Long timeline, String usrId);
}
