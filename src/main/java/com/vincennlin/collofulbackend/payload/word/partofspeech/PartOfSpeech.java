package com.vincennlin.collofulbackend.payload.word.partofspeech;

import lombok.Getter;

import java.util.Set;

@Getter
public enum PartOfSpeech {

    N("n.", "Noun", "名詞", SubPart.C, SubPart.U),
    PRON("pron.", "Pronoun", "代名詞"),
    V("v.", "Verb", "動詞", SubPart.VT, SubPart.VI),
    ADV("adv.", "Adverb", "副詞"),
    ADJ("adj.", "Adjective", "形容詞"),
    PREP("prep.", "Preposition", "介系詞"),
    CONJ("conj.", "Conjunction", "連接詞"),
    DET("det.", "Determiner", "限定詞"),
    INTERJ("interj.", "Interjection", "感嘆詞"),
    NUM("num.", "Numeral", "數詞"),
    PHR("phr.", "Phrase", "片語"),
    ABBR("abbr.", "Abbreviation", "縮寫"),;

    private final String abbreviation;
    private final String full;
    private final String chinese;
    private final Set<SubPart> subParts;

    PartOfSpeech(String abbreviation, String full, String chinese, SubPart... subParts) {
        this.abbreviation = abbreviation;
        this.full = full;
        this.chinese = chinese;
        this.subParts = Set.of(subParts);
    }
}
