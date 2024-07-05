package etu.spb.etu.Internet_news_newspaper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import etu.spb.etu.Internet_news_newspaper.exception.ErrorHandler;
import etu.spb.etu.Internet_news_newspaper.exception.IdNotFoundException;
import etu.spb.etu.Internet_news_newspaper.like.Like;
import etu.spb.etu.Internet_news_newspaper.like.service.LikeService;
import etu.spb.etu.Internet_news_newspaper.post.PostController;
import etu.spb.etu.Internet_news_newspaper.post.dto.CommentDto;
import etu.spb.etu.Internet_news_newspaper.post.dto.PostDto;
import etu.spb.etu.Internet_news_newspaper.post.dto.PostFullDto;
import etu.spb.etu.Internet_news_newspaper.post.mapper.PostMapper;
import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import etu.spb.etu.Internet_news_newspaper.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PostController.class)
@ContextConfiguration(classes = {PostController.class, ErrorHandler.class})
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PostService postService;

    @MockBean
    private LikeService likeService;

    private String URL;

    @BeforeEach
    void setUp() {
        URL = "http://localhost:8080/posts";
    }


    @Test
    @WithMockUser
    void getById() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(1L)
                .build();

        CommentDto comment = CommentDto.builder()
                .id(1L)
                .text("text")
                .userName("authorName")
                .createdComment(LocalDateTime.now())
                .build();

        List<CommentDto> comments = Collections.singletonList(comment);

        Like like = Like.builder()
                .id(1L)
                .postId(1L)
                .userId(1L)
                .build();

        List<Like> likes = Collections.singletonList(like);

        PostFullDto result = PostMapper.postToPostFullDto(post,comments,likes);

        when(postService.getById(anyLong()))
                .thenReturn(result);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/{id}"), 1L));

        response.andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser
    void getByIdThrowException() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(1L)
                .build();

        CommentDto comment = CommentDto.builder()
                .id(1L)
                .text("text")
                .userName("authorName")
                .createdComment(LocalDateTime.now())
                .build();

        List<CommentDto> comments = Collections.singletonList(comment);

        Like like = Like.builder()
                .id(1L)
                .postId(1L)
                .userId(1L)
                .build();

        List<Like> likes = Collections.singletonList(like);

        PostFullDto result = PostMapper.postToPostFullDto(post,comments,likes);

        when(postService.getById(anyLong()))
                .thenThrow(IdNotFoundException.class);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/{id}"), 1L));

        response.andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void getPosts() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(1L)
                .build();

        CommentDto comment = CommentDto.builder()
                .id(1L)
                .text("text")
                .userName("authorName")
                .createdComment(LocalDateTime.now())
                .build();


        Like like = Like.builder()
                .id(1L)
                .postId(1L)
                .userId(1L)
                .build();

        List<CommentDto> comments = Collections.singletonList(comment);
        List<Like> likes = Collections.singletonList(like);


        Post secondPost = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(1L)
                .build();

        CommentDto secondComment = CommentDto.builder()
                .id(1L)
                .text("text")
                .userName("authorName")
                .createdComment(LocalDateTime.now())
                .build();


        Like secondLike = Like.builder()
                .id(1L)
                .postId(1L)
                .userId(1L)
                .build();

        List<CommentDto> anotherComments = Collections.singletonList(secondComment);
        List<Like> anotherLikes = Collections.singletonList(secondLike);

        PostFullDto firstPostFullDto = PostMapper.postToPostFullDto(post, comments, likes);
        PostFullDto secondPostFullDto = PostMapper.postToPostFullDto(post, comments, likes);

        List<PostFullDto> posts = new ArrayList<>();

        posts.add(firstPostFullDto);
        posts.add(secondPostFullDto);


        when(postService.getPosts())
                .thenReturn(new ArrayList<>(posts));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL));

        response.andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser
    void createPost() throws Exception {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .created(LocalDateTime.now())
                .userId(1L)
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(1L)
                .build();

        when(postService.createPost(postDto))
                .thenReturn(postDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL));

        response.andExpect(status().is2xxSuccessful());
    }

}
