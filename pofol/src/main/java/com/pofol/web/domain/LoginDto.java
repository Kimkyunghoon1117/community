package com.pofol.web.domain;

import java.util.Objects;

public class LoginDto {
	private String id;
	private String pw;
	private String name;
	private int gradeNo;
	private String gradeName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(int gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	@Override
	public String toString() {
		return "LoginDto [id=" + id + ", pw=" + pw + ", name=" + name + ", gradeNo=" + gradeNo + ", gradeName="
				+ gradeName + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(gradeName, gradeNo, id, name, pw);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginDto other = (LoginDto) obj;
		return Objects.equals(gradeName, other.gradeName) && gradeNo == other.gradeNo && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(pw, other.pw);
	}
}
