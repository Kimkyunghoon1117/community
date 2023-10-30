package com.pofol.web.dao;

import java.util.List;
import java.util.Map;

import com.pofol.web.domain.ImageDto;
import com.pofol.web.domain.ImageFileDto;
import com.pofol.web.domain.SearchCondition;

public interface ImageDao {
	int count() throws Exception;
	int deleteAll() throws Exception;
	int delete(ImageDto dto) throws Exception;

    // 이미지 View
	ImageDto select(Integer no) throws Exception;
    // 이미지 list 가져오기
	List<ImageFileDto> getImageFile(Integer no) throws Exception;
	
    // write
    int write(ImageDto dto) throws Exception;
    // writeFile - 자바에서 foreach
    int writeFile(ImageFileDto dto) throws Exception;
    
    int update(ImageDto dto) throws Exception;
    int increaseViewCnt(Integer no) throws Exception;

    List<ImageDto> selectPage(Map map) throws Exception;
    List<ImageDto> selectAll() throws Exception;
    
    int searchResultCnt(SearchCondition sc) throws Exception;
    List<ImageDto> searchSelectPage(SearchCondition sc) throws Exception;
    // 이미지 바꾸기 - fno, fileName
	int changeImage(ImageFileDto fileDto);
	// 이미지 삭제하기 - fno, id
	int deleteImage(Integer fno, String id);
}