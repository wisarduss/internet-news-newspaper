package etu.spb.etu.Internet_news_newspaper.post.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "photo_URL")
    private String photoURL;
    private final LocalDateTime created = LocalDateTime.now();
    @Column(name = "user_id")
    private Long userId;
}
