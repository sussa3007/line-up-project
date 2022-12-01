package com.toyproject.lineupproject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "phoneNumber"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false, unique = true)
    private String nickname;

    @Setter
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Setter
    private List<String> roles = new ArrayList<>();

    @Setter
    @Column
    private String phoneNumber;


    @Setter
    private String memo;

    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LoginBase loginBase;


    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "admin")
    private final Set<AdminPlaceMap> adminPlaceMaps = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("requestId")
    @OneToMany(mappedBy = "admin")
    private final Set<Request> requests = new LinkedHashSet<>();


    public void addAdminPlaceMaps(AdminPlaceMap adminPlaceMap) {
        adminPlaceMaps.add(adminPlaceMap);
    }

    public void addRequests(Request request) {
        requests.add(request);
    }

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;


    protected Admin() {}

    protected Admin(String email, String nickname, String password, String phoneNumber, String memo, Status status, LoginBase loginBase) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.memo = memo;
        this.status = status;
        this.loginBase = loginBase;

    }
    public Admin(String email, String nickname, String password, String phoneNumber, String memo) {
    }

    public static Admin of(
            String email,
            String nickname,
            String password,
            String phoneNumber,
            String memo,
            Status status,
            LoginBase loginBase) {
        return new Admin(email, nickname, password, phoneNumber, memo, status, loginBase);
    }
    public static Admin of(
            String email,
            String nickname,
            String password,
            String phoneNumber,
            String memo) {
        return new Admin(email, nickname, password, phoneNumber, memo);
    }

    public void updateEntity(Admin admin) {
        this.email = Optional.ofNullable(admin.getEmail()).orElse(this.email);
        this.nickname = Optional.ofNullable(admin.getNickname()).orElse(this.nickname);
        this.password = Optional.ofNullable(admin.getPassword()).orElse(this.password);
        this.phoneNumber = Optional.ofNullable(admin.getPhoneNumber()).orElse(this.phoneNumber);
        this.memo = Optional.ofNullable(admin.getMemo()).orElse(this.memo);
        this.status = Optional.ofNullable(admin.getStatus()).orElse(this.status);
        this.loginBase = Optional.ofNullable(admin.getLoginBase()).orElse(this.loginBase);
        this.roles = Optional.ofNullable(admin.getRoles()).orElse(this.roles);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return id != null && id.equals(((Admin) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nickname, phoneNumber, createdAt, modifiedAt);
    }


    public enum Status {
        ACTIVE_USER(1,"Active"),
        INACTIVE_USER(2,"InActive");

        private int status;

        private String message;

        Status(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum LoginBase{
        BASIC_LOGIN(1,"Basic Login"),
        SOCIAL_LOGIN(2,"Social Login");

        private int status;

        private String message;

        LoginBase(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

    }

}