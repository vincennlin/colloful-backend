package com.vincennlin.collofulbackend.entity.word;

import com.vincennlin.collofulbackend.entity.user.User;
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

    public Word(User user, String name, PartOfSpeech partOfSpeech) {
        this.user = user;
        this.name = name;
        this.partOfSpeech = partOfSpeech;
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
