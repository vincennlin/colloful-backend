package com.vincennlin.collofulbackend.entity.word;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "collocations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"content", "definition_id"})
)
public class Collocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 100)
    private String content;

    @Column(name = "meaning", length = 100)
    private String meaning;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "definition_id", referencedColumnName = "id")
    private Definition definition;

    @OneToMany(
            mappedBy = "collocation",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Sentence> sentences;
}
