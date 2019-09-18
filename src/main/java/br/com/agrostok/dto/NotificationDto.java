package br.com.agrostok.dto;

public class NotificationDto {
	
	private Long id;
	private String message;
	private Boolean readed;
	private String timeSended;

	
	public NotificationDto(String message, Boolean readed, Long id, String timeSended) {
		super();
		this.message = message;
		this.readed = readed;
		this.id = id;
		this.timeSended = timeSended;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getReaded() {
		return readed;
	}

	public void setReaded(Boolean readed) {
		this.readed = readed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTimeSended() {
		return timeSended;
	}

	public void setTimeSended(String timeSended) {
		this.timeSended = timeSended;
	}
	
	
	
}
