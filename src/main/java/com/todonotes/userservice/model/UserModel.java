package com.todonotes.userservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.todonotes.noteservice.model.Note;
import com.todonotes.utils.Phone;

@Entity(name="user")
@Cacheable
public class UserModel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Size(min=2,max=30)
	private String name;
	@NotEmpty
	@Email
	private String email;
	@NotNull
	@Size(min=6)
	private String password;
	@NotNull
	@Phone
	private String mobile;
	private boolean isVerified=false;
	@OneToMany(orphanRemoval=true,fetch=FetchType.EAGER)
	@JoinColumn(name="id")
	@Cascade(CascadeType.ALL)
	private List<Note> notes = new ArrayList<>();
	
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public boolean isVerified() {
		return isVerified;
	}
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	
	
}
