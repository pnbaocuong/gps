package com.example.demo.service;

import java.util.Arrays;

public enum GPSType {
	KML("kml"), GPX("gpx"), PLT("plt"), GDB("gdb");
	private final String value;

	private GPSType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public static boolean isValid(String fileExtension) {
		return Arrays.stream(GPSType.values()).anyMatch(e ->e.getValue().equals(fileExtension));
	}
}