package bw.batch.example.blog.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Byungwook Lee on 2018-03-18
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Entity
public class Post {
	@Id
	@GeneratedValue
	private long id;
	private String subject;
	@Column(length = 1000_000_000)
	private String content;
	private LocalDateTime registerDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}
}
