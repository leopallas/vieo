package com.demo.service;

import com.demo.constants.PhotoConstants;
import com.demo.db.Indicator;
import com.demo.exception.BadRequestException;
import com.demo.util.IdGeneratorHelper;
import com.demo.vo.LoginResultVO;
import com.demo.vo.LoginVO;
import com.demo.vo.PictureVO;
import com.demo.vo.RegisterVO;
import com.demo.vo.UserVO;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class PPMServiceTest {
    public static void main(String[] args) {
        final PPMService ppm = PPMServiceManager.getService("localhost", 8080);
        String deviceSerialNo = "ffffffff-fd46-fe82-8a07-dd19246097f9";
        String barCode = "18435301";
        final String usrId = "101";
        String password = "101";

        try {
            // 注册
            RegisterVO registerVO = new RegisterVO();
            registerVO.setBarcode(barCode);
            registerVO.setSerialNO(deviceSerialNo);
            ppm.register(registerVO);

            UserVO[] userVOs = ppm.getUsers();
            PPMServiceTest.printArray("User List", userVOs);

            // 登录
//            LoginVO loginVO = new LoginVO();
//            loginVO.setUsrId(usrId);
//            loginVO.setPwd(password);
//            loginVO.setSerialNo(deviceSerialNo);
//            LoginResultVO resultVO = ppm..login(loginVO);
//            System.out.println(String.format("Login Result :::> %s", resultVO.toString()));

            // 上传图片
//            final String zipFilePath = "d:\\upload\\100000.zip";
//            final String filePath = "d:\\upload\\74037105.jpg";
            final String zipFilePath = "/home/xw/Documents/100000.zip";
            final String filePath = "/home/xw/Documents/100000.jpg";
            for (int i = 0; i < 1; i++) {
                final int x = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PictureVO picturevo = new PictureVO();
                        picturevo.setPicId(IdGeneratorHelper.getID());
                        picturevo.setUserId(usrId);
                        picturevo.setPicType(Indicator.PICTURE_TYPE_NORMAL.getCode());;
                        picturevo.setPicDesc("test desc ");
                        // picturevo.setLat(new Double(100));
                        // picturevo.setLon(new Double(200));
                        picturevo.setCreateDt(new Date().getTime());
                        try {
                            if(x%2 == 0) {
                                picturevo.setPicName("new picture.jpg");
                                picturevo.setSodDesc("new sound.3pg");
                                ppm.uploadPicture(picturevo, zipFilePath);
                            } else {
                                picturevo.setPicName("100000.jpg");
                                ppm.uploadPicture(picturevo, filePath);
                            }
                        } catch (BadRequestException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            //
            // boolean init = true;
            // //得到全部图片
            // if(init) {
            // PictureVO[] pictureVOs = ppm.getSyncPictures(null);
            // downloadResource(ppm, pictureVOs);
            // } else {
            // //得到timeline后到当前的图片
            // long timeline = 1365149466000L;
            // ppm.heartbeat(timeline, null);
            // if(ppm.heartbeat(timeline, new LocationVO(123, 234, 1))) {
            // PictureVO[] pictureVOs = ppm.getSyncPictures(timeline);
            // downloadResource(ppm, pictureVOs);
            // }
            // }

            // ppm.logout();
        } catch (BadRequestException e) {
            System.out.println(e.getDescription());
            e.printStackTrace();
        }
    }

    private static void downloadResource(final PPMService ppm, PictureVO[] pictureVOs) {
        final File downloadRootPath = new File("d:\\upload\\download");
        // final File downloadRootPath = new
        // File("/home/xw/Documents/download/");
        for (final PictureVO pictureVO : pictureVOs) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] bytes = ppm.getPicture(pictureVO);
                        saveByteArrayToFile(downloadRootPath, pictureVO.getUserId(), pictureVO.getPicId(),
                                pictureVO.getPicName(), bytes);
                    } catch (BadRequestException e) {
                        e.printStackTrace();
                        System.out.println(e.getDescription());
                    }

                    try {

                        byte[] bytes = ppm.getThumbnailPicture(pictureVO);
                        saveByteArrayToFile(downloadRootPath, pictureVO.getUserId(), pictureVO.getPicId(),
                                pictureVO.getPicName() + PhotoConstants.THUMB_SMALL_NAME, bytes);
                    } catch (BadRequestException e) {
                        e.printStackTrace();
                        System.out.println(e.getDescription());
                    }
                    try {
                        byte[] bytes = ppm.getSoundResource(pictureVO);
                        saveByteArrayToFile(downloadRootPath, pictureVO.getUserId(), pictureVO.getPicId(),
                                pictureVO.getSodDesc(), bytes);
                    } catch (BadRequestException e) {
                        e.printStackTrace();
                        System.out.println(e.getDescription());
                    }
                }
            }).start();
        }
    }

    private static void saveByteArrayToFile(File rootPath, String usrId, String fileId, String fileName, byte[] buffer) {
        try {
            File filePath = rootPath;
            if (!StringUtils.isEmpty(usrId)) {
                filePath = new File(filePath, usrId);
            }
            if (!StringUtils.isEmpty(fileId)) {
                filePath = new File(filePath, fileId);
            }
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            FileOutputStream out = new FileOutputStream(new File(filePath, fileName));
            BufferedOutputStream bufferOut = new BufferedOutputStream(out);
            bufferOut.write(buffer);
            bufferOut.flush();
            bufferOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> void printArray(String message, T[] objects) {
        if (objects == null || objects.length == 0) {
            return;
        }
        int index = 0;
        for (T object : objects) {
            System.out.println(String.format("[%s][%s] [%d] :::> %s", message, object.getClass().getName(), index,
                    object.toString()));
            index++;
        }
    }
}
