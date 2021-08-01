package com.example.demo.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class LinkDTO {

	private String href;

	private String text;

	public LinkDTO() {
	}

	public LinkDTO(String href, String text) {
		this.href = href;
		this.text = text;
	}

	public String getHref() {
		return href;
	}

	@XmlAttribute(name = "href")
	public void setHref(String href) {
		this.href = href;
	}

	public String getText() {
		return text;
	}

	@XmlElement(name = "text")
	public void setText(String text) {
		this.text = text;
	}

}
