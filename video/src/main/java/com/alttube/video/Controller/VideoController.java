package com.alttube.video.Controller;

import com.alttube.video.Models.Category;
import com.alttube.video.Models.Video;
import com.alttube.video.Repository.VideoRepository;
import com.alttube.video.Services.VideoManager;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jms.JMSException;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Set;

@CrossOrigin(exposedHeaders = "token", allowCredentials = "true")
@RestController
public class VideoController {

    private final VideoRepository videoRepository;
    private final VideoManager videoManager;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public VideoController(VideoRepository videoRepository, VideoManager videoManager, JmsTemplate jmsTemplate) {
        this.videoRepository = videoRepository;
        this.videoManager = videoManager;
        this.jmsTemplate = jmsTemplate;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(value = "/video/category/{category}")
    public Set<Video> getVideosByCategory(@PathVariable(value = "category") Category category) {
        Set<Video> videos = videoRepository.findByCategory(category);
        for(Video video : videos)
            video.setImage(Base64.getEncoder().encode(setImage(video)));

        return videos;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(value = "/video/keyword/{keyword}")
    public Set<Video> getVideosByKeyword(@PathVariable(value = "keyword") String input) {
        Set<Video> videos = videoRepository.findByTitle(input.toLowerCase());
        for(Video video : videos)
            video.setImage(Base64.getEncoder().encode(setImage(video)));

        return videos;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(value = "/account/videos/id/{id}")
    public Set<Video> getAccountVideos(@PathVariable(value = "id") Long owner) {
        Set<Video> videos = videoRepository.findByOwner(owner);
        for(Video video : videos)
            video.setImage(Base64.getEncoder().encode(setImage(video)));

        return videos;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/video/stream/{stream}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public FileSystemResource getVideoStream(@PathVariable(value = "stream") String path) {
        return new FileSystemResource(Paths.get("").toAbsolutePath().toString() + "/video-videos/" + path);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/video",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveVideo(
            @RequestHeader(name = "token", defaultValue = "Empty") String headerToken,
            @CookieValue(name = "jwt", defaultValue = "Empty") String jwt,
            @CookieValue(name = "token", defaultValue = "Empty") String cookieToken,
            @Valid Video vid,
            @RequestPart(required = false) MultipartFile thumbnail,
            @RequestPart(required = false) MultipartFile video) {

//        authenticateCredentials(headerToken, cookieToken, jwt);
        videoManager.saveImage(thumbnail, vid);
        vid.setTitle(vid.getTitle().toLowerCase());
        videoManager.saveVideo(video, vid);
        videoRepository.save(vid);
        return "{\"status\": \"successful\"}";
    }

    private void authenticateCredentials(String headerToken, String cookieToken, String jwt) {
        String credentials = headerToken + " " + cookieToken + " " + jwt;
        ActiveMQTextMessage msg = (ActiveMQTextMessage)jmsTemplate.sendAndReceive(
                "CommentAuthentication", session -> session.createTextMessage(credentials) );
        try {
            if(!msg.getText().equals("Authenticated")) {
                throw new RuntimeException(msg.getText());
            }
        } catch (JMSException ex) { throw new RuntimeException("Server Error!, ApacheMQ failure, please contact administrator"); }
    }

    private byte[] setImage(Video video) {
        Path path = Paths.get(Paths.get("").toAbsolutePath().toString() + "/video-images/" + video.getImgRef());
        try {
            return Files.readAllBytes(path);
        } catch (IOException ex) {return null;}
    }
}
