package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostDbStore;

import java.util.List;

@ThreadSafe
@Service
public class PostService {
    private final PostDbStore postStore;
    private final CityService cityService;

    private PostService(PostDbStore postStore, CityService cityService) {
        this.postStore = postStore;
        this.cityService = cityService;
    }

    public List<Post> findAll() {
        List<Post> posts = postStore.findAll();
        posts.forEach(post -> post.setCity(cityService.findById(post.getCity().getId())));
        return posts;
    }

    public void add(Post post) {
        postStore.add(post);
    }

    public Post findById(int id) {
        Post rsl = postStore.findById(id);
        rsl.setCity(cityService.findById(rsl.getCity().getId()));
        return rsl;
    }

    public void update(Post post) {
        postStore.update(post);
    }
}
