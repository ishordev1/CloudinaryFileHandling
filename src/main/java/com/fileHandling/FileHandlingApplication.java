package com.fileHandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@SpringBootApplication
public class FileHandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileHandlingApplication.class, args);
	}

	@Bean
	public Cloudinary getCloudinary() {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", "daabanzir",
				"api_key", "365652442124874",
				"api_secret", "I_RS6PNow3kgghxxS9e6LNUx1LE",
				"secure", true));
	
	return cloudinary;
}
}