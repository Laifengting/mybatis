package com.lft.mybatis.chapter03.entity;

/**
 * Class Name:      Employee
 * Package Name:    com.lft.mybatis.entity
 * <p>
 * Function: 		A {@code Employee} object With Some FUNCTION.
 * Date:            2021-04-28 9:57
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class Employee {
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", gender='" + gender + '\'' +
				'}';
	}
}
