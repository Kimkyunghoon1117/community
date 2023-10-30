package com.pofol.web.service;

import java.util.List;
import java.util.Map;

import com.pofol.web.domain.ImageDto;
import com.pofol.web.domain.ImageFileDto;
import com.pofol.web.domain.SearchCondition;

public interface ImageService {
	//list
	public List<ImageDto> list() throws Exception;
	
	//view
	public ImageDto view(Integer no) throws Exception;
	
	public List<ImageFileDto> getImageFile(Integer no) throws Exception;

	//write
	public int write(ImageDto dto, List<ImageFileDto> fileList) throws Exception;
	
	//update
	public int update(ImageDto dto) throws Exception;
	
	public int updateFile(ImageFileDto dto) throws Exception;
		
	//delete
	public int delete(ImageDto dto) throws Exception;
	
	public int getCount() throws Exception;
	public List<ImageDto> getPage(Map map) throws Exception;

	public int getSearchResultCnt(SearchCondition sc) throws Exception;
	public List<ImageDto> getSearchResultPage(SearchCondition sc) throws Exception;
	
	// 이미지 바꾸기
	public int changeImage(ImageFileDto fileDto);
	// 이미지 삭제
	public int deleteImage(Integer fno, String id);
}
