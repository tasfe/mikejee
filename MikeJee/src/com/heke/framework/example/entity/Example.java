package com.heke.framework.example.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
@Entity
@Table(name = "t_example")
public class Example implements Serializable {
	
	private int id;
	
	private String name;
	
	private String theCode;

	private String theContent;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "the_code")
	public String getTheCode() {
		return theCode;
	}

	public void setTheCode(String theCode) {
		this.theCode = theCode;
	}

	@Column(name = "the_content")
	public String getTheContent() {
		return theContent;
	}

	public void setTheContent(String theContent) {
		this.theContent = theContent;
	}
}
