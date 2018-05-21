package com.alttube.video.Controller;

import com.alttube.video.Models.Category;
import com.alttube.video.Models.Video;
import com.alttube.video.Repository.VideoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@RestController
public class VideoController {

    private final VideoRepository videoRepository;

    public VideoController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
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
            @RequestHeader("token") String token,
            HttpServletRequest request,
            @Valid Video video,
            @RequestParam(name = "img")MultipartFile img,
            @RequestParam(name = "vid")MultipartFile vid) {
        videoRepository.save(video);
    }
}
