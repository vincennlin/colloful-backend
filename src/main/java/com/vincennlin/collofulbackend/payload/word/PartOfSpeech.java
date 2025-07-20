package com.vincennlin.collofulbackend.payload.word;

import lombok.Getter;

@Getter
public enum PartOfSpeech {

    N("n.", "Noun", "名詞"),
    PRON("pron.", "Pronoun", "代名詞"),
    VT("vt.", "Transitive Verb", "及物動詞"),
    VI("vi.", "Intransitive Verb", "不及物動詞"),
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

    PartOfSpeech(String abbreviation, String full, String chinese) {
        this.abbreviation = abbreviation;
        this.full = full;
        this.chinese = chinese;
    }
}
