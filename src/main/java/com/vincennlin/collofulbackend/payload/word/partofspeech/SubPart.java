package com.vincennlin.collofulbackend.payload.word.partofspeech;

public enum SubPart {

    C("c.", "Countable Noun", "可數名詞"),
    U("u.", "Uncountable Noun", "不可數名詞"),
    VT("vt.", "Transitive Verb", "及物動詞"),
    VI("vi.", "Intransitive Verb", "不及物動詞"),;

    private final String abbreviation;
    private final String full;
    private final String chinese;

    SubPart(String abbreviation, String full, String chinese) {
        this.abbreviation = abbreviation;
        this.full = full;
        this.chinese = chinese;
    }
}
