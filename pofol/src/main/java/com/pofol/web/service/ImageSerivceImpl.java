package com.pofol.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pofol.web.dao.ImageDao;
import com.pofol.web.domain.ImageDto;
import com.pofol.web.domain.ImageFileDto;
import com.pofol.web.domain.SearchCondition;
/*
 * 자동 생성 - @Controller, @Service, @Repository, @Conponent, @Advice, @RestController
 * servlet-context.xml, root-context.xml에 설정되어 있어야만 한다. 
 */
@Service
// 동일한 타입의 객체가 있는 경우 설정
@Qualifier("imageServiceImpl")
public class ImageSerivceImpl implements ImageService {
	@Autowired
	ImageDao imageDao;
	@Override
	public List<ImageDto> list() throws Exception {
		return imageDao.selectAll();
	}
	@Override
	public ImageDto view(Integer no) throws Exception {
        return imageDao.select(no);
    }
	@Override
	public List<ImageFileDto> getImageFile(Integer no) throws Exception {
        return imageDao.getImageFile(no);
    }
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int write(ImageDto dto, List<ImageFileDto> fileList) throws Exception {
		// 1. dto 저장
		imageDao.write(dto);
		//throw new Exception();
		for(ImageFileDto fileDto : fileList) {
			System.out.println("imageDto : "+dto);
			fileDto.setNo(dto.getNo());
			imageDao.writeFile(fileDto);
		}
		return 1;
	}
	@Override
	public int update(ImageDto dto) throws Exception {
		return imageDao.update(dto);
	}
	
	@Override
	public int updateFile(ImageFileDto dto) throws Exception {
		//return imageDao.update(dto);
		return 0;
	}

	@Override
	public int delete(ImageDto dto) throws Exception {
		return imageDao.delete(dto);
	}

	@Override
	public int getCount() throws Exception {
		return imageDao.count();
	}

	@Override
	public List<ImageDto> getPage(Map map) throws Exception {
		return imageDao.selectPage(map);
	}

	@Override
	public int getSearchResultCnt(SearchCondition sc) throws Exception {
		return imageDao.searchResultCnt(sc);
	}

	@Override
	public List<ImageDto> getSearchResultPage(SearchCondition sc) throws Exception {
		return imageDao.searchSelectPage(sc);
	}
	@Override
	public int changeImage(ImageFileDto fileDto) {
		return imageDao.changeImage(fileDto);
	}
	@Override
	public int deleteImage(Integer fno, String id) {
		return imageDao.deleteImage(fno, id);
	}
	
}
