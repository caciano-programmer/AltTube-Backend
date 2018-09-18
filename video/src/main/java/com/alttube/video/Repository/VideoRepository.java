package com.alttube.video.Repository;

import com.alttube.video.Models.Category;
import com.alttube.video.Models.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface VideoRepository extends CrudRepository<Video, Long> {

    Set<Video>  findByTitle(String title);
    Set<Video>  findByOwner(Long owner);
    Set<Video>  findByCategory(Category category);
}
