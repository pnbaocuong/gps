package com.example.demo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	
	
	public static File convertToFile(MultipartFile multipart,String tempFolder) throws IOException {
		File convFile = new File(tempFolder + FileSystems.getDefault().getSeparator() + multipart.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipart.getBytes());
		fos.close();
		return convFile;
	}

}
