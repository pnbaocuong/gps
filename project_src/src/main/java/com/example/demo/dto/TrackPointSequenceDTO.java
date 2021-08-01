package com.example.demo.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class TrackPointSequenceDTO {

	private List<TrackPointDTO> trackPoints;

	public TrackPointSequenceDTO() {
	}

	public TrackPointSequenceDTO(List<TrackPointDTO> trackPoints) {
		this.trackPoints = trackPoints;
	}

	@XmlElementWrapper(name = "trkseg")
	@XmlElement(name = "trkpt")
	public void setTrackPoints(List<TrackPointDTO> trackPoints) {
		this.trackPoints = trackPoints;
	}

	public List<TrackPointDTO> getTrackPoints() {
		return trackPoints;
	}
}
