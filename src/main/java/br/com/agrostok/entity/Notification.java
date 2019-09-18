package br.com.agrostok.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1554100397236384208L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "msg")
	private String msg;

	@Column(name = "readed")
	private Boolean readed;
	
	@Column(name = "deleted")
	private Boolean deleted;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "user_created_id")
	private Long userCreatedId;

	@Column(name = "user_updated_id")
	private Long userUpdatedId;

	public Notification() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getReaded() {
		return readed;
	}

	public void setReaded(Boolean readed) {
		this.readed = readed;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUserCreatedId() {
		return userCreatedId;
	}

	public void setUserCreatedId(Long userCreatedId) {
		this.userCreatedId = userCreatedId;
	}

	public Long getUserUpdatedId() {
		return userUpdatedId;
	}

	public void setUserUpdatedId(Long userUpdatedId) {
		this.userUpdatedId = userUpdatedId;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	
}
