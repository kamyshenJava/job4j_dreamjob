package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger index = new AtomicInteger(1);
    private PostStore() {
        add(new Post("Junior Java Job", "only for students"));
        add(new Post("Middle Java Job", "for experienced programmers"));
        add(new Post("Senior Java Job", "special education is required"));
    }
    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        int idx = index.getAndIncrement();
        post.setId(idx);
        post.setCreated(LocalDate.now());
        posts.put(idx, post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
