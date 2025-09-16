package org.example.sj_jws_indv_inlamning.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String authorId;

    @Column(nullable = false)
    private String authorUsername;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "content", length = 200, nullable = false)
    private String content;

    public Post(){}
    public Post(Long id, String authorId, String authorUsername, String title, String content) {
        this.id = id;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.title = title;
        this.content = content;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getAuthorId() {return authorId;}
    public void setAuthorId(String authorId) {this.authorId = authorId;}

    public String getAuthorUsername() {return authorUsername;}
    public void setAuthorUsername(String authorUsername) {this.authorUsername = authorUsername;}

    public String getTitle() {return title;}
    public void setTitle(String user) {this.title = user;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
}
