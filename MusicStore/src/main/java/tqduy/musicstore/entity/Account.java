package tqduy.musicstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Account")
public class Account {
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="UserName", nullable = false)
	private String userName;
	
	@Column(name="PassWord", nullable = false)
	private String passWord;
	
	@Column(name="Role", nullable = false)
	private String role;

	public Account() {
	}

	public Account(int id, String userName, String passWord, String role) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
