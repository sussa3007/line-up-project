package com.toyproject.lineupproject.domain;

import com.toyproject.lineupproject.dto.AdminEventRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class AdminEventMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private Admin admin;

    @Setter
    @ManyToOne(optional = false)
    private Event event;

    @Setter
    @Column(nullable = false)
    private Integer requestNumberOfPeople;


    @Setter
    @Column(nullable = true)
    private String  memo;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    protected AdminEventMap() {}

    protected AdminEventMap(Admin admin, Event event,
                            Integer requestNumberOfPeople,
                            String memo) {
        this.admin = admin;
        this.event = event;
        this.requestNumberOfPeople = requestNumberOfPeople;
        this.memo = memo;
    }

    public static AdminEventMap of(Admin admin, Event event,
                                   AdminEventRequest request) {
        return new AdminEventMap(admin, event, request.requestNumberOfPeople(), request.memo());
    }

    public void addAdminEventMap() {
        this.admin.addAdminEventMaps(this);
        this.event.addAdminEventMaps(this);
    }

    public void updateEntity(AdminEventRequest request) {
        this.event.modifyCurrentNumberOfPeople(this.requestNumberOfPeople, request.requestNumberOfPeople());
        this.memo = request.memo();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return id != null && id.equals(((AdminEventMap) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, admin, createdAt, modifiedAt);
    }
}
