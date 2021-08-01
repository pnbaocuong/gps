package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.GPSDTO;
import com.example.demo.dto.GPXDTO;
import com.example.demo.dto.LinkDTO;
import com.example.demo.dto.MetadataDTO;
import com.example.demo.entity.GPS;
import com.example.demo.repository.GPSRepository;

@Service
public class GPSService {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${upload.folder.temp}")
	private String tempFolder;

	@Autowired
	private GPSRepository gpsRepository;

	public void insertData(MultipartFile multipart)
			throws IOException, InvalidFileNameException, DataIntegrityViolationException, JAXBException {
		
		//Detect file type
		GPSType gpsFileType = detectFileType(multipart);
		
		//parser from suitable format and save to database
		GPSDTO gpsDto;
		if (gpsFileType == GPSType.GPX) {

			gpsDto = GPXDTO.unmarshalFile(multipart, tempFolder);
			GPS gpsEntity = gpsDto.convertToEntity();
			gpsRepository.save(gpsEntity);

		} else {
			
			//more support file gps will be extend here

			throw new InvalidFileNameException(multipart.getOriginalFilename(), "Not support this extension now!");

		}

	}

	private GPSType detectFileType(MultipartFile multipart) {
		String fileExtension = FilenameUtils.getExtension(multipart.getOriginalFilename());
		if (!GPSType.isValid(fileExtension)) {
			throw new InvalidFileNameException(multipart.getOriginalFilename(), "Invalid File extension");
		}

		return GPSType.valueOf(fileExtension);
	}

	public Page<GPXDTO> getLatestTracks(int page, int size) {
		Page<GPS> result = gpsRepository.findAll(new PageRequest(page, size, Direction.DESC, "time"));

		List<GPS> gpsList = result.getContent();
		List<GPXDTO> gpsModels = new ArrayList<>();
		for (int i = 0; i < gpsList.size(); i++) {
			GPS entity = gpsList.get(i);
			GPXDTO gpsModel = new GPXDTO();
			gpsModel.setId(entity.getId());
			MetadataDTO metadatModel = new MetadataDTO(entity.getName(), entity.getDesc(), entity.getAuthor(),
					entity.getTime());
			LinkDTO link = new LinkDTO(entity.getLinkHref(), entity.getLinkText());
			metadatModel.setLink(link);
			gpsModel.setMetadata(metadatModel);

			gpsModels.add(gpsModel);
		}
		return new PageImpl<>(gpsModels, new PageRequest(page, size), result.getTotalElements());
	}

	@Transactional(readOnly = true)
	public GPXDTO getGPSDetail(long id) {
		GPXDTO model = new GPXDTO();

		GPS result = gpsRepository.findOne(id);
		if (result != null) {
			return model.convertFromEntity(result);
		}

		return model;
	}

}
