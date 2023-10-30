package com.pofol.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pofol.web.domain.ImageDto;
import com.pofol.web.domain.ImageFileDto;
import com.pofol.web.domain.SearchCondition;

@Repository
public class ImageDaoImpl implements ImageDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.pofol.web.dao.ImageMapper.";

    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    } // T selectOne(String statement)

    @Override
    public int deleteAll() {
        return session.delete(namespace+"deleteAll");
    } // int delete(String statement)

    @Override
    public int delete(ImageDto dto) throws Exception {
        return session.delete(namespace+"delete", dto);
    } // int delete(String statement, Object parameter)
    
    @Override
    public int write(ImageDto dto) throws Exception {
        return session.insert(namespace+"write", dto);
    } // int insert(String statement, Object parameter)

    @Override
    public int writeFile(ImageFileDto dto) throws Exception {
    	return session.insert(namespace+"writeFile", dto);
    }
    @Override
    public int update(ImageDto dto) throws Exception {
    	return session.update(namespace+"update", dto);
    } // int update(String statement, Object parameter)
    
    @Override
    public int increaseViewCnt(Integer no) throws Exception {
    	return session.update(namespace+"increaseViewCnt", no);
    } // int update(String statement, Object parameter)
   
    @Override
    public List<ImageDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    } // List<E> selectList(String statement)
    // 이미지 보기 - 텍스트 데이터
    @Override
    public ImageDto select(Integer no) throws Exception {
        return session.selectOne(namespace + "select", no);
    } // T selectOne(String statement, Object parameter)
    
    // 이미지 보기 - 이미지파일 데이터
    @Override
    public List<ImageFileDto> getImageFile(Integer no) throws Exception {
    	return session.selectList(namespace+"getImageFile", no);
    }
    @Override
    public List<ImageDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace+"selectPage", map);
    } // List<E> selectList(String statement, Object parameter)

	@Override
    public int searchResultCnt(SearchCondition sc) throws Exception {
        //System.out.println("sc in searchResultCnt() = " + sc);
        //System.out.println("session = " + session);
        return session.selectOne(namespace+"searchResultCnt", sc);
    } // T selectOne(String statement, Object parameter)

    @Override
    public List<ImageDto> searchSelectPage(SearchCondition sc) throws Exception {
        return session.selectList(namespace+"searchSelectPage", sc);
    } // List<E> selectList(String statement, Object parameter)

	@Override
	public int changeImage(ImageFileDto fileDto) {
		return session.update(namespace+"changeImage", fileDto);
	}

	@Override
	public int deleteImage(Integer fno, String id) {
		Map map = new HashMap();
		map.put("fno", fno);
		map.put("id", id);
		return session.delete(namespace+"deleteImage", map);
	}


}
