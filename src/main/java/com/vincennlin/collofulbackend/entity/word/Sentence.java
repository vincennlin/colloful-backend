package com.vincennlin.collofulbackend.entity.word;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sentences")
public class Sentence {

    public Sentence(String content, String translation, Collocation collocation) {
        this.content = content;
        this.translation = translation;
        this.collocation = collocation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String content;

    @Column(length = 100)
    private String translation;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "collocation_id", referencedColumnName = "id")
    private Collocation collocation;

    public Long getUserId() {
        return collocation.getUserId();
    }
}
