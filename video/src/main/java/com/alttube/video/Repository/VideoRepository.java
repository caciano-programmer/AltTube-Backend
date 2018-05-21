package com.alttube.video.Repository;

import com.alttube.video.Models.Category;
import com.alttube.video.Models.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface VideoRepository extends CrudRepository<Video, Long> {

    Set<Video> findByOwner(String owner);
    Set<Video> findByCategory(Category category);
    Set<Video> findByKeywordsIn(String... keywords);
    Set<Video> findByCategoryOrKeywordsIn(Category category, String... keywords);
}
