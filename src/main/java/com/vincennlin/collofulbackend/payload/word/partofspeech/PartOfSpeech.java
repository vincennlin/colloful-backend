package com.vincennlin.collofulbackend.payload.word.partofspeech;

import lombok.Getter;

import java.util.Set;

@Getter
public enum PartOfSpeech {

    NOUN("n.", "名詞", SubPart.COUNTABLE_NOUN, SubPart.UNCOUNTABLE_NOUN),
    PRONOUN("pron.", "代名詞"),
    VERB("v.", "動詞", SubPart.TRANSITIVE_VERB, SubPart.INTRANSITIVE_VERB),
    ADVERB("adv.", "副詞"),
    ADJECTIVE("adj.", "形容詞"),
    PREPOSITION("prep.", "介系詞"),
    CONJUNCTION("conj.", "連接詞"),
    DETERMINER("det.", "限定詞"),
    INTERJECTION("interj.", "感嘆詞"),
    NUMERAL("num.", "數詞"),
    PHASE("phr.", "片語"),
    ABBREVIATION("abbr.", "縮寫"),;

    private final String abbreviation;
    private final String chinese;
    private final Set<SubPart> subParts;

    PartOfSpeech(String abbreviation, String chinese, SubPart... subParts) {
        this.abbreviation = abbreviation;
        this.chinese = chinese;
        this.subParts = Set.of(subParts);
    }
}
