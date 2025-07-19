package com.vincennlin.collofulbackend.payload.word.partofspeech;

public enum SubPart {

    COUNTABLE_NOUN("c.", "可數名詞"),
    UNCOUNTABLE_NOUN("u.", "不可數名詞"),
    TRANSITIVE_VERB("vt.", "及物動詞"),
    INTRANSITIVE_VERB("vi.", "不及物動詞"),;

    private final String abbreviation;
    private final String chinese;

    SubPart(String abbreviation, String chinese) {
        this.abbreviation = abbreviation;
        this.chinese = chinese;
    }
}
