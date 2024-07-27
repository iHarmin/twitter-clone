package com.group06.twitter2;

import com.group06.twitter2.model.Post;
import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.PostsRepository;
import com.group06.twitter2.service.Implementations.PostServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {
    @InjectMocks
    PostServiceImpl postService;
    @Mock
    PostsRepository postsRepository;

    @Test
    public void getPostsTest() {
        ArrayList<Post> MockPosts = new ArrayList<>();
        int numPosts = 5;
        for (int i = 0; i<numPosts; i++) {
            Post post = new Post();
            post.setBody("Post" + i);
            MockPosts.add(post);
        }
        when(postsRepository.findAll()).thenReturn(MockPosts);
        ArrayList<Post> posts = postService.getPosts();
        assertEquals(numPosts, posts.size());
        assertEquals("Post0", posts.get(0).getBody());
    }

    @Test
    public void createPostTest() {
        Post post = new Post();
        post.setBody("postBody");
        when(postsRepository.save(post)).thenReturn(post);
        postService.createPost(post);
        assertEquals("postBody", post.getBody());
    }

    @Test
    public void findPostsByUsersIDTest() {
        Twitter2 userID = new Twitter2(0, "Name0", "n0@dal.ca", "password0",
                "FName0", "LName0", "rec0", "int0",
                "active", "Student", Twitter2.RequestStatus.APPROVED);
        ArrayList<Post> mockPosts = new ArrayList<>();
        for (int i = 0; i<5; i++) {
            Post post = new Post();
            post.setBody("postBody" + i);
            post.setUserID(userID);
            mockPosts.add(post);
        }
        when(postsRepository.findByUserID(userID)).thenReturn(mockPosts);
        ArrayList<Post> posts = postService.findPostsByUserID(userID);
        assertEquals(5, posts.size());
        assertEquals("Name0", posts.get(0).getUserID().getUserName());
        assertEquals("postBody0", posts.get(0).getBody());
    }
}
