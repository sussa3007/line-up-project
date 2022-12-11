package com.toyproject.lineupproject.domain;

import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.exception.GeneralException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;


@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, length = 5000)
    private String post;

    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Admin admin;

    @Setter
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Place place;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Post.Status status;


    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;


    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modifiedAt;




    public void addAdmin(Admin admin) {
        this.admin = admin;
        admin.addPost(this);
    }

    public void addPlace(Place place) {
        this.place = place;
        place.addPost(this);
    }

    public void updateEntity(Post post) {
        if (this.password.equals(post.password)) {
            this.admin = Optional.ofNullable(post.getAdmin()).orElse(this.admin);
            this.title = Optional.ofNullable(post.getTitle()).orElse(this.title);
            this.post = Optional.ofNullable(post.getPost()).orElse(this.post);
            Place findPlace = Optional.ofNullable(post.getPlace()).orElse(this.place);
            if (!findPlace.equals(this.place)) {
                this.addPlace(findPlace);
            }
            this.status = Optional.ofNullable(post.getStatus()).orElse(this.status);
        } else {
            throw new GeneralException(ErrorCode.POST_ACCESS_DENIED);
        }

    }

    public Post() {
    }

    public Post(
            Admin admin,
            Place place,
            String title,
            String post,
            String password,
            Post.Status status
    ) {
        this.admin = admin;
        this.place = place;
        this.title = title;
        this.post = post;
        this.status = status;
        this.password = password;
    }

    public Post(
            Admin admin,
            String title,
            String post,
            String password,
            Post.Status status
    ) {
        this.admin = admin;
        this.title = title;
        this.post = post;
        this.status = status;
        this.password = password;
    }
    public Post(
            String title,
            String post,
            String password
    ) {
        this.title = title;
        this.post = post;
        this.password = password;
    }

    public static Post of(
            Admin admin,
            Place place,
            String title,
            String post,
            String password,
            Post.Status status
    ) {
        return new Post(admin,place,title,post,password,status);
    }

    public static Post of(
            Admin admin,
            String title,
            String post,
            String password,
            Post.Status status
    ) {
        return new Post(admin,title,post,password,status);
    }

    public static Post of(
            String title,
            String post,
            String password
    ) {
        return new Post(title,post,password);
    }

    @Getter
    @RequiredArgsConstructor
    public enum Status {

        NOTICE,
        REVIEW,
        PLACE_NOTICE;

    }
}
