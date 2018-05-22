package com.alttube.video.Services;

import com.alttube.video.Models.Video;
import org.springframework.web.multipart.MultipartFile;

public interface VideoManager {
   void saveImage(MultipartFile file, Video video);
   void saveVideo(MultipartFile file, Video video);
}
