package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger index = new AtomicInteger(1);

    private PostStore() {
        add(new Post("Junior Java Job", "only for students"));
        add(new Post("Middle Java Job", "for experienced programmers"));
        add(new Post("Senior Java Job", "special education is required"));
    }
    public static PostStore instOf() {
        return INST;
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
