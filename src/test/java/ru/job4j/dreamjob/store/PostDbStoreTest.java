package ru.job4j.dreamjob.store;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDbStoreTest {
    @Test
    public void whenCreatePost() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post("Java Job", "description", new City(1));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenCreateAndUpdatePost() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post("Java Job", "description", new City(1));
        store.add(post);
        store.update(new Post("Java", "description2", new City(2)));
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is("Java"));
        assertThat(postInDb.getDescription(), is("description2"));
        assertThat(postInDb.getCity().getId(), is(2));
    }

    @Test
    public void whenCreatePostsAndFindAll() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post("Java Job", "description", new City(1));
        Post post2 = new Post("Java", "description2", new City(2));
        Post post3 = new Post("Java3", "description3", new City(3));
        store.add(post);
        store.add(post2);
        store.add(post3);
        assertThat(store.findAll().size(), is(3));
    }
}