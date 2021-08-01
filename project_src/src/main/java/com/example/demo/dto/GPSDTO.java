package com.example.demo.dto;

import com.example.demo.entity.GPS;


public interface GPSDTO {
	GPS convertToEntity();
	GPXDTO convertFromEntity(GPS gps);
}
