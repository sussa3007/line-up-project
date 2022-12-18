package com.toyproject.lineupproject.domain;

import com.toyproject.lineupproject.constant.RequestCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Admin admin;

    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RequestCode requestCode;

    @Column(nullable = true)
    @Setter
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

    public void updateEntity(Request request) {
        this.admin = Optional.ofNullable(request.getAdmin()).orElse(this.admin);
        this.requestCode = Optional.ofNullable(request.getRequestCode()).orElse(this.requestCode);
        this.message = Optional.ofNullable(request.getMessage()).orElse(this.message);
        this.status = Optional.ofNullable(request.getStatus()).orElse(this.status);
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

    public Request(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public Request() {
    }


    @Getter
    @RequiredArgsConstructor
    public enum Status {


        OPEN_ISSUE("OPEN"),
        IN_PROGRESS_ISSUE("INPROGRESS"),
        CLOSE_ISSUE("CLOSE");


        private final String message;

    }

    public static Request of(
            RequestCode requestCode,
            String message,
            Request.Status status
    ) {

        return new Request(requestCode, message, status);
    }
    public static Request of(
            String message,
            Request.Status status
    ) {

        return new Request(message, status);
    }
    public static Request of(
            Admin admin,
            RequestCode requestCode,
            String message,
            Request.Status status
    ) {
        Request request = new Request(requestCode, message, status);
        request.addAdmin(admin);
        return request;
    }



}
