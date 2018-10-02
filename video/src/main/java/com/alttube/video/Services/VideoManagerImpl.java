package com.alttube.video.Services;

import com.alttube.video.Models.Video;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class VideoManagerImpl implements VideoManager {

    private final String imgPath = Paths.get("").toAbsolutePath().toString() + "/video-images";
    private final String vidPath = Paths.get("").toAbsolutePath().toString() + "/video-videos";

    @Override
    public void saveImage(MultipartFile multipartFile, Video video) {
        String uniqueFileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        File file = new File(imgPath, uniqueFileName);

        try {
            file.createNewFile();
            multipartFile.transferTo(file);
            if(ImageIO.read(file) == null || file.length() > (2000 * 1000)) {
                file.delete();
                throw new RuntimeException("Failed, image must be correct format and under 2MB!");
            }
            video.setImgRef(uniqueFileName);
        } catch (IOException ex) { ex.printStackTrace(); }
    }

    @Override
    public void saveVideo(MultipartFile multipartFile, Video video) {
        String uniqueFileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        File file = new File(vidPath, uniqueFileName);

        try {
            file.createNewFile();
            multipartFile.transferTo(file);
            if(!validVideo(file)) {
                file.delete();
                throw new RuntimeException("Failed, video must be correct format(mp4, webm, ogg) and under 1GB.");
            }
            video.setVidRef(uniqueFileName);
        } catch (IOException ex) { ex.printStackTrace(); }
    }

    private boolean validVideo(File file) throws IOException {
        Tika tika = new Tika();
        String filetype = tika.detect(file);
        String type = filetype.split("/")[0];
        String ext = filetype.split("/")[1];
        if(!type.equals("video") && !ext.equals("webm") && !ext.equals("mp4") && !ext.equals("ogg") && file.length() > (1000000 * 1000))
            return false;

        return true;
    }
}
