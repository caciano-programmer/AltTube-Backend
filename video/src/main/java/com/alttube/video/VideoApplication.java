package com.alttube.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Paths;

@SpringBootApplication
public class VideoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoApplication.class, args);
		new File(Paths.get("").toAbsolutePath().toString() + "/video-images").mkdir();
		new File(Paths.get("").toAbsolutePath().toString() + "/video-videos").mkdir();
	}
}
