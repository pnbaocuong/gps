package com.example.demo.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class MetadataDTO {

	private String name;

	private String desc;

	private String author;

	private Date time;
 
	private LinkDTO link;

	public MetadataDTO() {

	}

	public MetadataDTO(String name, String desc, String author, Date time) {
		this.name = name;
		this.desc = desc;
		this.author = author;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public LinkDTO getLink() {
		return link;
	}

	@XmlElement(name = "link")
	public void setLink(LinkDTO link) {
		this.link = link;
	}

}
