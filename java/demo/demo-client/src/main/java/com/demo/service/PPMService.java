package com.demo.service;

import com.demo.exception.BadRequestException;
import com.demo.vo.LocationVO;
import com.demo.vo.LoginResultVO;
import com.demo.vo.LoginVO;
import com.demo.vo.PictureVO;
import com.demo.vo.RegisterVO;
import com.demo.vo.UserVO;

import java.io.IOException;

public interface PPMService {
    public void register(RegisterVO registerVO) throws BadRequestException;

    public UserVO[] getUsers() throws BadRequestException;

//    public LoginResultVO login(LoginVO loginVO) throws BadRequestException;

//    public boolean heartbeat(long timeline, LocationVO location) throws BadRequestException;

    public PictureVO uploadPicture(PictureVO pictureVO, String filePath) throws BadRequestException, IOException;

    public PictureVO uploadPictureFlag(PictureVO pictureVO) throws BadRequestException;

//    public PictureVO[] getSyncPictures(Long timeline) throws BadRequestException;

    public byte[] getPicture(PictureVO pictureVO) throws BadRequestException;

    public byte[] getThumbnailPicture(PictureVO pictureVO) throws BadRequestException;

    public byte[] getSoundResource(PictureVO pictureVO) throws BadRequestException;

//    public void logout() throws BadRequestException;
}
