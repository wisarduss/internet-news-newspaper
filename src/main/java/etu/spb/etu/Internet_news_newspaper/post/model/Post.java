package etu.spb.etu.Internet_news_newspaper.post.model;

import etu.spb.etu.Internet_news_newspaper.user.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Post {

    private Long id;
    private String title;
    private String description;
    private String photoURL;
    private LocalDateTime publicationTime = LocalDateTime.now();
    private User owner;
}
