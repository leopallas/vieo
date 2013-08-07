package com.demo.web.dao;

import com.demo.web.domain.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PictureMapper {
    int deleteByPrimaryKey(String picId);

    int insert(Picture record);

    int insertSelective(Picture record);

    Picture selectByPrimaryKey(String picId);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);

    int getNumberOfPictureAfterTimeline(@Param("timeline") Date timeline, @Param("currentDt") Date currentDt);

    List<Picture> selectPictureList();

    List<Picture> selectPictureListAfterTimeline(@Param("timeline") Date timeline, @Param("currentDt") Date currentDt,
                                                 @Param("usrId") String usrId);

    Map<String, Double> getGPSLocation(@Param("usrId") String usrId);
}