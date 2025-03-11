package com.fileHandling.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.fileHandling.service.FileService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/file")
public class FileController {
	@Value("${MediaFilePath}")
	String mediaFilePath;
	@Autowired
	private FileService fileService;
	
	@Autowired
	private Cloudinary cloudnary;

	@PostMapping
	public ResponseEntity<String> addFile(@RequestParam("file") MultipartFile file) throws IOException {
		String name = fileService.saveFile(file,mediaFilePath);
		return new ResponseEntity<>(name, HttpStatus.CREATED);
	}
	
	@PostMapping("/cloud")
	public ResponseEntity<Map> uploadFileInCloud(@RequestParam("file") MultipartFile file) throws IOException{
		Map upload = this.cloudnary.uploader().upload(file.getBytes(), Map.of());
		return new ResponseEntity<Map>(upload,HttpStatus.OK);
	}
	
	@PutMapping("/{fileName}")
	public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file, @PathVariable String fileName) throws IOException{
		String name=this.fileService.updateFile(file, mediaFilePath, fileName);
		return new ResponseEntity<String>(name, HttpStatus.OK);
	}
	
	@DeleteMapping("/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) throws IOException{
		this.fileService.deleteFile(mediaFilePath, fileName);
		return new ResponseEntity<String>("delete file successfully",HttpStatus.OK);
	}
	
	@GetMapping("{fileName}")
	public void getFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
		InputStream file = this.fileService.readFile(mediaFilePath, fileName);
		StreamUtils.copy(file, response.getOutputStream());
	}
	
}
