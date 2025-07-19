package com.vincennlin.collofulbackend.entity.word;

import com.vincennlin.collofulbackend.payload.word.partofspeech.PartOfSpeech;
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
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "part_of_speech", nullable = false)
    private PartOfSpeech partOfSpeech;

    @OneToMany(
            mappedBy = "word",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Definition> definitions;
}
