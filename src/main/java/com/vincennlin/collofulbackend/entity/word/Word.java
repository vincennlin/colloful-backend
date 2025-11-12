package com.vincennlin.collofulbackend.entity.word;

import com.vincennlin.collofulbackend.entity.review.ReviewInfo;
import com.vincennlin.collofulbackend.entity.user.User;
import com.vincennlin.collofulbackend.payload.review.ReviewOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "words",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user_id"})
)
public class Word {

    public Word(User user, String name) {
        this.user = user;
        this.name = name;
        this.important = true;
        this.definitions = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Column(name = "important")
    private boolean important;

    @Column(name = "mistaken")
    private boolean mistaken;

    @Column(name = "review_today")
    private boolean reviewToday;

    @CreationTimestamp
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(
            mappedBy = "word",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Definition> definitions;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private ReviewInfo reviewInfo;

    public void review(ReviewOption option) {
        this.reviewInfo.review(option);
    }
}
