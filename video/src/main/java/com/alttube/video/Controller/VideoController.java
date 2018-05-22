package com.alttube.video.Controller;

import com.alttube.video.Models.Category;
import com.alttube.video.Models.Video;
import com.alttube.video.Repository.VideoRepository;
import com.alttube.video.Services.VideoManager;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jms.JMSException;
import javax.validation.Valid;
import java.util.Set;

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
    @GetMapping(value = "/video")
    public Set<Video> getVideos (
            @RequestParam(name = "category", required = false) Category category,
            @RequestParam(name = "keywords", required = false) String... keywords) {

        if(category != null && keywords != null && keywords.length > 0) return videoRepository.findByCategoryOrKeywordsIn(category, keywords);
        else if(keywords != null && keywords.length > 0) return videoRepository.findByKeywordsIn(keywords);
        else return videoRepository.findByCategory(category);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(value = "/account/videos")
    public Set<Video> getAccountVideos(String owner) {
        return videoRepository.findByOwner(owner);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/video",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveVideo(
            @RequestHeader(name = "token", defaultValue = "Empty") String headerToken,
            @CookieValue(name = "jwt", defaultValue = "Empty") String jwt,
            @CookieValue(name = "token", defaultValue = "Empty") String cookieToken,
            @Valid Video video,
            @RequestParam(name = "img")MultipartFile img,
            @RequestParam(name = "vid")MultipartFile vid) {

        authenticateCredentials(headerToken, cookieToken, jwt);
        videoManager.saveImage(img, video);
        videoManager.saveVideo(vid, video);
        videoRepository.save(video);
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
}
