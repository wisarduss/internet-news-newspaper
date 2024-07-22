package etu.spb.etu.Internet.news.newspaper.post.model;

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
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "photo_URL", nullable = false)
    private String photoURL;
    private final LocalDateTime created = LocalDateTime.now();
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
