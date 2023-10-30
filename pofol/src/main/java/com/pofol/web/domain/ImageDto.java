package com.pofol.web.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

public class ImageDto {
    private Integer no;
    private String  title;
    private String  content;
    private String  id;
    private Date    reg_date;
    // list 할때 기본 이미지 한개가 필요하다.: image_file table
    private String fileName;
    // 바이너리 단위의 실제적인 파일객체
    // 대표 1개
    private MultipartFile mainImageFile;
	// 서브 0~5
    private MultipartFile[] imageFile;
    
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public MultipartFile getMainImageFile() {
		return mainImageFile;
	}
	public void setMainImageFile(MultipartFile mainImageFile) {
		this.mainImageFile = mainImageFile;
	}
	public MultipartFile[] getImageFile() {
		return imageFile;
	}
	public void setImageFile(MultipartFile[] imageFile) {
		this.imageFile = imageFile;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(imageFile);
		result = prime * result + Objects.hash(content, fileName, id, mainImageFile, no, reg_date, title);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageDto other = (ImageDto) obj;
		return Objects.equals(content, other.content) && Objects.equals(fileName, other.fileName)
				&& Objects.equals(id, other.id) && Arrays.equals(imageFile, other.imageFile)
				&& Objects.equals(mainImageFile, other.mainImageFile) && Objects.equals(no, other.no)
				&& Objects.equals(reg_date, other.reg_date) && Objects.equals(title, other.title);
	}
	@Override
	public String toString() {
		return "ImageDto [no=" + no + ", title=" + title + ", content=" + content + ", id=" + id + ", reg_date="
				+ reg_date + ", fileName=" + fileName + ", mainImageFile=" + mainImageFile + ", imageFile="
				+ Arrays.toString(imageFile) + "]";
	}
}