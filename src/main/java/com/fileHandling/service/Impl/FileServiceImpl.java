package com.fileHandling.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fileHandling.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String saveFile(MultipartFile file, String path) throws IOException {
		String orgName = file.getOriginalFilename();
		String ext = orgName.substring(file.getOriginalFilename().lastIndexOf("."));
		String tempName = UUID.randomUUID().toString();
		String tempNameWithExt = tempName + ext;
		String pathWithTempNameWithExt = path + tempNameWithExt;

		if (!Files.exists(Paths.get(path))) {
			Files.createDirectory(Paths.get(path));
		}
		Files.copy(file.getInputStream(), Paths.get(pathWithTempNameWithExt));
		return tempNameWithExt;
	}

	@Override
	public void deleteFile(String path, String fileName) throws IOException {

		if (!Files.exists(Paths.get(path + fileName))) {
			throw new IOException("file not found on path:" + Paths.get(path + fileName));
		}
		Files.delete(Paths.get(path + fileName));

	}

	@Override
	public String updateFile(MultipartFile file, String path, String fileName) throws IOException {
		deleteFile(path, fileName);
		return	saveFile(file,path);
		
	}

	@Override
	public InputStream readFile(String path,String fileName) throws IOException {
		if(!Files.exists(Paths.get(path+fileName))) {
			throw new IOException("file not found:"+path);
		}
		
	
		return Files.newInputStream(Paths.get(path+fileName));
		
	}

}


