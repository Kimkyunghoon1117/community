package com.pofol.web.domain;

import java.util.*;

public class ImageFileDto {
    private int fno;
    private int no;
    private String fileName;
    // 1 - 주 이미지, 0 - 부 이미지
    private int pr;
    
    public ImageFileDto() {
    	this.pr =0;
    };
	public ImageFileDto(int fno, int no, String fileName, int pr) {
		super();
		this.fno = fno;
		this.no = no;
		this.fileName = fileName;
		this.pr = pr;
	}
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getPr() {
		return pr;
	}
	public void setPr(int pr) {
		this.pr = pr;
	}
	@Override
	public String toString() {
		return "ImageFileDto [fno=" + fno + ", no=" + no + ", fileName=" + fileName + ", pr=" + pr + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(fileName, fno, no, pr);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageFileDto other = (ImageFileDto) obj;
		return Objects.equals(fileName, other.fileName) && fno == other.fno && no == other.no && pr == other.pr;
	}
    
    
}