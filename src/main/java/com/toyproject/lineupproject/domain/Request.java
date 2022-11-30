package com.toyproject.lineupproject.domain;

import com.toyproject.lineupproject.constant.RequestCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Admin admin;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RequestCode requestCode;

    @Column(nullable = true)
    private String message;


    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Request.Status status;


    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;


    public void addAdmin(Admin admin) {
        this.admin = admin;
        admin.addRequests(this);
    }

    public Request(
            Admin admin,
            RequestCode requestCode,
            String message,
            Request.Status status
    ) {
        this.admin = admin;
        this.requestCode = requestCode;
        this.message = message;
        this.status = status;
    }
    public Request(
            RequestCode requestCode,
            String message,
            Request.Status status
    ) {
        this.requestCode = requestCode;
        this.message = message;
        this.status = status;
    }

    public Request() {
    }


    public enum Status {


        OPEN_ISSUE("요청"),
        IN_PROGRESS_ISSUE("처리중"),
        CLOSE_ISSUE("처리 완료");

        @Getter
        private String message;

        Status(String message) {
        }
    }

    public static Request of(
            RequestCode requestCode,
            String message,
            Request.Status status
    ) {

        return new Request(requestCode, message, status);
    }
    public static Request of(
            Admin admin,
            RequestCode requestCode,
            String message,
            Request.Status status
    ) {

        return new Request(admin,requestCode, message, status);
    }

}
