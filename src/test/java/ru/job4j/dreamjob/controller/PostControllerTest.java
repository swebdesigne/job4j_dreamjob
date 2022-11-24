package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;


class PostControllerTest {
    private PostService postService;
    private CityService cityService;
    private PostController postController;
    private HttpSession session;
    private Model model;

    @BeforeEach
    public void initPostController() {
        postService = mock(PostService.class);
        cityService = mock(CityService.class);
        session = mock(HttpSession.class);
        model = mock(Model.class);
        postController = new PostController(
                postService,
                cityService
        );
    }

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "New Description", new City(1, "Moscow"), true, LocalDateTime.now()),
                new Post(2, "New post", "New Description", new City(2, "Rostov"), true, LocalDateTime.now())
        );
        when(postService.findAll()).thenReturn(posts);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenFormAddPost() {
        List<City> cities = Arrays.asList(new City(1, "Moscow"), new City(1, "Rostov"));
        when(cityService.getAllCities()).thenReturn(cities);
        String page = postController.formAddPost(model, session);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(1, "New post", "New post desc", new City(1, "Moscow"), true, LocalDateTime.now());
        String page = postController.createPost(post);
        verify(postService).add(post);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFormUpdatePost() {
        Post post = new Post(1, "New post", "New Description", new City(1, "Moscow"), true, LocalDateTime.now());
        when(postService.findById(post.getId())).thenReturn(post);
        String page = postController.formUpdatePost(model, post.getId(), session);
        verify(model).addAttribute("post", post);
        assertThat(page, is("updatePost"));
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(1, "New post", "New Description", new City(1, "Moscow"), true, LocalDateTime.now());
        String page = postController.updatePost(input);
        verify(postService).update(input);
        assertThat(page, is("redirect:/posts"));
    }

}