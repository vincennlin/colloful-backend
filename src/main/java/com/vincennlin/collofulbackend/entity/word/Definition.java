package com.vincennlin.collofulbackend.entity.word;

import com.vincennlin.collofulbackend.payload.word.PartOfSpeech;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "definitions"
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"meaning", "word_id"})
//        }
)
public class Definition {

    public Definition(String meaning, PartOfSpeech partOfSpeech, Word word) {
        this.meaning = meaning;
        this.partOfSpeech = partOfSpeech;
        this.word = word;
        this.collocations = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String meaning;

    @Enumerated(EnumType.STRING)
    @Column(name = "part_of_speech", nullable = false)
    private PartOfSpeech partOfSpeech;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    private Word word;

    @OneToMany(
            mappedBy = "definition",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Collocation> collocations;

    public Long getUserId() {
        return word.getUser().getId();
    }
}
