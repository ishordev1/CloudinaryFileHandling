package com.fileHandling.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;



public interface FileService {
String saveFile(MultipartFile file, String path) throws IOException;
String updateFile(MultipartFile file, String path, String fileName) throws IOException;
void deleteFile(String path,String fileName) throws IOException;
InputStream readFile(String path,String fileName) throws IOException;
}
