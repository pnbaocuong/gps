package com.example.demo.dto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.GPS;
import com.example.demo.entity.TrackPoint;
import com.example.demo.entity.WayPointTrack;
import com.example.demo.utils.FileUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "gpx")
@JsonInclude(Include.NON_NULL)
public class GPXDTO implements GPSDTO{

	@XmlTransient
	private Long id;

	@XmlElement(name = "metadata")
	private MetadataDTO metadata;

	@XmlElement(name = "wpt")
	private List<WayPointTrackDTO> wayPointTracks;

	@XmlElement(name = "trk")
	@JsonIgnore
	private TrackPointSequenceDTO trackPointSequence;

	@XmlTransient
	private List<TrackPointDTO> trackPoints;

	public GPXDTO() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GPS convertToEntity() {
		GPS gps = new GPS();

		gps.setId(this.id);
		gps.setAuthor(this.metadata.getAuthor());
		gps.setDesc(this.metadata.getDesc());
		gps.setName(this.metadata.getName());
		gps.setTime(this.metadata.getTime());
		gps.setLinkHref(this.metadata.getLink().getHref());
		gps.setLinkText(this.metadata.getLink().getText());

		List<WayPointTrack> wayPointTrackEntities = new ArrayList();
		for (int i = 0; i < this.wayPointTracks.size(); i++) {
			WayPointTrack entity = wayPointTracks.get(i).convertToEntity();
			entity.setGps(gps);
			wayPointTrackEntities.add(entity);
		}
		gps.setWayPointTracks(wayPointTrackEntities);

		List<TrackPoint> trackPointEntities = new ArrayList();
		List<TrackPointDTO> trackPointModels = this.trackPointSequence.getTrackPoints();
		for (int i = 0; i < trackPointModels.size(); i++) {
			TrackPoint entity = trackPointModels.get(i).convertToEntity();
			entity.setGps(gps);
			trackPointEntities.add(entity);
		}
		gps.setTrackPoints(trackPointEntities);

		return gps;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GPXDTO convertFromEntity(GPS gps) {
		this.setId(gps.getId());
		
		MetadataDTO meta = new MetadataDTO(gps.getName(), gps.getDesc(), gps.getAuthor(), gps.getTime());
		LinkDTO linkType = new LinkDTO(gps.getLinkHref(), gps.getLinkText());
		meta.setLink(linkType);
		this.setMetadata(meta);

		List<WayPointTrackDTO> wayPointTracks = new ArrayList();
		for (int i = 0; i < gps.getWayPointTracks().size(); i++) {
			WayPointTrack entity = gps.getWayPointTracks().get(i);
			WayPointTrackDTO model = new WayPointTrackDTO(entity.getId(), entity.getLat(), entity.getLon(),
					entity.getName(), entity.getSym());
			wayPointTracks.add(model);
		}
		this.setWayPointTracks(wayPointTracks);

		List<TrackPointDTO> trackPoints = new ArrayList();
		for (int i = 0; i < gps.getTrackPoints().size(); i++) {
			TrackPoint entity = gps.getTrackPoints().get(i);
			TrackPointDTO model = new TrackPointDTO(entity.getId(), entity.getLat(), entity.getLon(),
					entity.getEle(), entity.getTime());
			trackPoints.add(model);
		}
		this.setTrackPoints(trackPoints);

		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MetadataDTO getMetadata() {
		return metadata;
	}

	public void setMetadata(MetadataDTO metadata) {
		this.metadata = metadata;
	}

	public List<WayPointTrackDTO> getWayPointTracks() {
		return wayPointTracks;
	}

	public void setWayPointTracks(List<WayPointTrackDTO> wayPointTracks) {
		this.wayPointTracks = wayPointTracks;
	}

	public TrackPointSequenceDTO getTrackPointSequence() {
		return trackPointSequence;
	}

	public void setTrackPointSequence(TrackPointSequenceDTO trackPointSequence) {
		this.trackPointSequence = trackPointSequence;
	}

	public List<TrackPointDTO> getTrackPoints() {
		return trackPoints;
	}

	public void setTrackPoints(List<TrackPointDTO> trackPoints) {
		this.trackPoints = trackPoints;
	}
	public static GPXDTO unmarshalFile(MultipartFile multipart,String tempFolder) throws IOException, InvalidFileNameException, JAXBException {
		File file = FileUtils.convertToFile(multipart,tempFolder);

		JAXBContext jaxbContext = JAXBContext.newInstance(GPXDTO.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		GPXDTO gps = (GPXDTO) unmarshaller.unmarshal(file);

		return gps;
	}
}
