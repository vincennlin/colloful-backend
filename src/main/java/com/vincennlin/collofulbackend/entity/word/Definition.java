package com.vincennlin.collofulbackend.entity.word;

import com.vincennlin.collofulbackend.payload.word.partofspeech.SubPart;
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
        name = "definitions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"meaning", "word_id"})
        }
)
public class Definition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String meaning;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_part")
    private SubPart subPart;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    private Word word;

    @OneToMany(
            mappedBy = "definition",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Collocation> collocation;
}
