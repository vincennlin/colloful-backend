package com.vincennlin.collofulbackend.service.ai.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.collofulbackend.payload.word.PartOfSpeech;
import com.vincennlin.collofulbackend.payload.word.dto.CollocationDto;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import com.vincennlin.collofulbackend.payload.word.dto.SentenceDto;
import com.vincennlin.collofulbackend.payload.word.request.*;
import com.vincennlin.collofulbackend.service.ai.AiService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AiServiceImpl implements AiService {

    private final OpenAiChatModel openAiChatModel;

    private final ObjectMapper objectMapper;

    @Override
    public String generateResponse(String message) {

        Prompt prompt = new Prompt(message);

        ChatResponse response = openAiChatModel.call(prompt);

        return response.getResults().get(0).getOutput().getText();
    }

    @Override
    public CreateCollocationsForDefinitionRequest generateCollocationsForDefinition(GenerateCollocationsForDefinitionRequest request) {

        List<DefinitionCollocationExample> examples = getCollocationExamples();

        List<Message> messages = List.of(
                getGenerateCollocationsForDefinitionInitialSystemMessage(examples),
                getGenerateCollocationsForDefinitionFormatExampleMessage(examples),
                new UserMessage(getGenerateCollocationsForDefinitionRequestString(request))
        );

        Prompt prompt = new Prompt(messages);

        ChatResponse response = openAiChatModel.call(prompt);

        String responseContent = response.getResults().get(0).getOutput().getText();

        responseContent = preProcessJson(responseContent);

        try{
            return objectMapper.readValue(responseContent, CreateCollocationsForDefinitionRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + responseContent, e);
        }
    }

    @Override
    public CreateSentencesForCollocationRequest generateSentencesForCollocation(GenerateSentencesForCollocationRequest request) {

        List<DefinitionSentenceExample> examples = getCollocationSentenceExamples();

        List<Message> messages = List.of(
                getGenerateSentencesForCollocationInitialSystemMessage(examples),
                getGenerateSentencesForCollocationFormatExampleMessage(examples),
                new UserMessage(getGenerateSentencesForCollocationRequestString(request))
        );

        Prompt prompt = new Prompt(messages);

        ChatResponse response = openAiChatModel.call(prompt);

        String responseContent = response.getResults().get(0).getOutput().getText();

        responseContent = preProcessJson(responseContent);

        try{
            return objectMapper.readValue(responseContent, CreateSentencesForCollocationRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + responseContent, e);
        }
    }

    @Override
    public CreateWordWithDetailRequest generateWordFromContent(GenerateWordFromContentRequest request) {

        List<GenerateWordFromContentExample> examples = getGenerateWordFromContentExamples();

        List<Message> messages = List.of(
                getGenerateWordFromContentInitialSystemMessage(examples),
                getGenerateWordFromContentFormatExampleMessage(examples),
                new UserMessage(getGenerateWordFromContentRequestString(request))
        );

        Prompt prompt = new Prompt(messages);

        ChatResponse response = openAiChatModel.call(prompt);

        String responseContent = response.getResults().get(0).getOutput().getText();

        responseContent = preProcessJson(responseContent);

        try{
            return objectMapper.readValue(responseContent, CreateWordWithDetailRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + responseContent, e);
        }
    }

    private Message getGenerateCollocationsForDefinitionInitialSystemMessage(List<DefinitionCollocationExample> examples) {

        StringBuilder message = new StringBuilder("你的任務是根據收到的單字定義，生成與該單字相關的搭配詞。\\n" +
                "你會收到一個英文單字的名稱、詞性與定義，請根據這些資訊生成搭配詞。\\n" +
                "每個單字請生成一至三個搭配詞。\\n" +
                "每個搭配詞應包含其含義和至少一個例句。\\n" +
                "請注意，請確保「搭配詞的含義與單字的詞性與定義」相關聯，這是最重要的要求。" +
                "請勿生成非指定詞性的單字的搭配詞，例如若是及物動詞，請不要生成該單字的名詞或非及物動詞的搭配詞。\\n");

        message.append("以下是範例的輸入和輸出：");

        for (DefinitionCollocationExample example : examples) {
            try{
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                message.append(objectMapper.writeValueAsString(example));
            } catch (Exception e) {
                message.append("Exception occurred while writing example: ").append(e.getMessage());
            }
        }

        message.append("請注意，以上json回應僅為格式範例，實際生成的搭配詞請根據收到的單字生成。");

        return new SystemMessage(message.toString());
    }

    private Message getGenerateSentencesForCollocationInitialSystemMessage(List<DefinitionSentenceExample> examples) {

        StringBuilder message = new StringBuilder("你的任務是根據收到的搭配詞，生成與該搭配詞相關的例句。\\n" +
                "你會收到一個英文單字的搭配詞，請根據這些資訊生成例句。\\n" +
                "每個搭配詞請生成一個例句，並包含中文翻譯。\\n" +
                "請注意，請確保「例句的內容與搭配詞」相關聯，這是最重要的要求。\\n");

        message.append("以下是範例的輸入和輸出：");

        for (DefinitionSentenceExample example : examples) {
            try{
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                message.append(objectMapper.writeValueAsString(example));
            } catch (Exception e) {
                message.append("Exception occurred while writing example: ").append(e.getMessage());
            }
        }

        message.append("請注意，以上json回應僅為格式範例，實際生成的例句請根據收到的搭配詞生成。");

        return new SystemMessage(message.toString());
    }

    private Message getGenerateCollocationsForDefinitionFormatExampleMessage(List<DefinitionCollocationExample> examples) {

        CreateCollocationsForDefinitionRequest exampleOutput = examples.get(2).getExampleOutput();

        String exampleOutputString;

        try {
            exampleOutputString = objectMapper.writeValueAsString(exampleOutput);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert example output to JSON: " + e.getMessage(), e);
        }

        return new SystemMessage(
                "以下是一個範例的json格式回應：\n\n" +
                        exampleOutputString + "\n\n" +
                        "請注意，以上json回應僅為格式範例，實際生成的搭配詞內容請依照收到的單字生成。\n\n"
        );
    }

    private Message getGenerateSentencesForCollocationFormatExampleMessage(List<DefinitionSentenceExample> examples) {

        CreateSentencesForCollocationRequest exampleOutput = examples.get(0).getExampleOutput();

        String exampleOutputString;

        try {
            exampleOutputString = objectMapper.writeValueAsString(exampleOutput);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert example output to JSON: " + e.getMessage(), e);
        }

        return new SystemMessage(
                "以下是一個範例的json格式回應：\n\n" +
                        exampleOutputString + "\n\n" +
                        "請注意，以上json回應僅為格式範例，實際生成的例句內容請依照收到的搭配詞生成。\n\n"
        );
    }

    private List<DefinitionCollocationExample> getCollocationExamples() {

        List<DefinitionCollocationExample> examples = new ArrayList<>();

        DefinitionDto definition1 = new DefinitionDto("curb", "路緣", PartOfSpeech.N);

        CollocationDto collocation1 = new CollocationDto("curb market", "路邊市場");
        SentenceDto sentence1 = new SentenceDto("The curb market offers fresh produce and local goods.", "路邊市場提供新鮮的農產品和當地商品。");
        collocation1.getSentences().add(sentence1);

        DefinitionCollocationExample example1 = new DefinitionCollocationExample(definition1, List.of(collocation1));
        examples.add(example1);


        DefinitionDto definition2 = new DefinitionDto("curb", "控制；限制，約束；抑制", PartOfSpeech.N);

        CollocationDto collocation2 = new CollocationDto("put a curb on …", "控制⋯⋯");
        CollocationDto collocation3 = new CollocationDto("put a curb on temper/spending habits", "控制壞脾氣／揮霍無度的習慣");
        SentenceDto sentence2 = new SentenceDto("You must try to put a curb on your bad temper/spending habits.", "你必須試著控制你的壞脾氣／揮霍無度的習慣。");
        collocation3.getSentences().add(sentence2);

        DefinitionCollocationExample example2 = new DefinitionCollocationExample(definition2, List.of(collocation2, collocation3));
        examples.add(example2);


        DefinitionDto definition3 = new DefinitionDto("refrain", "避免、忍住、克制", PartOfSpeech.VI);

        CollocationDto collocation4 = new CollocationDto("refrain from doing sth", "克制、避免做某事");
        SentenceDto sentence3 = new SentenceDto("Please refrain from smoking in the building.", "請在大樓內避免吸煙。");
        collocation4.getSentences().add(sentence3);
        CollocationDto collocation5 = new CollocationDto("refrain from making comments", "避免評論");
        SentenceDto sentence4 = new SentenceDto("It's best to refrain from making any comments until all the facts are known.", "在所有事實都清楚之前，最好避免評論。");
        collocation5.getSentences().add(sentence4);
        CollocationDto collocation6 = new CollocationDto("refrain from engaging in arguments", "避免參與爭論");
        SentenceDto sentence5 = new SentenceDto("In order to maintain a peaceful environment, we should all refrain from engaging in arguments.", "為了維持和平的環境，我們都應該避免參與爭論。");
        collocation6.getSentences().add(sentence5);

        DefinitionCollocationExample example3 = new DefinitionCollocationExample(definition3, List.of(collocation4, collocation5, collocation6));
        examples.add(example3);

        DefinitionDto definition4 = new DefinitionDto("deter", "阻撓、威嚇", PartOfSpeech.VT);

        CollocationDto collocation7 = new CollocationDto("deter sb/sth from doing sth", "阻止某人做某事");
        SentenceDto sentence6 = new SentenceDto("The high security measures deterred criminals from attempting to break into the building.", "高強度的安全措施阻止了罪犯試圖闖入大樓。");
        collocation7.getSentences().add(sentence6);
        CollocationDto collocation8 = new CollocationDto("deter sb from committing a crime", "阻止某人犯罪");
        SentenceDto sentence7 = new SentenceDto("The harsh penalties for theft are meant to deter people from committing crimes.", "偷竊的嚴厲懲罰旨在阻止人們犯罪。");
        collocation8.getSentences().add(sentence7);

        DefinitionCollocationExample example4 = new DefinitionCollocationExample(definition4, List.of(collocation7, collocation8));
        examples.add(example4);

        return examples;
    }

    private List<DefinitionSentenceExample> getCollocationSentenceExamples() {

        List<DefinitionSentenceExample> examples = new ArrayList<>();

        DefinitionDto definition = new DefinitionDto("curb", "控制；限制，約束；抑制", PartOfSpeech.N);

        CollocationDto collocation = new CollocationDto("put a curb on …", "控制⋯⋯");
        definition.getCollocations().add(collocation);

        SentenceDto sentence = new SentenceDto("You must try to put a curb on your bad spending habits.", "你必須試著控制你揮霍無度的壞習慣。");

        DefinitionSentenceExample example = new DefinitionSentenceExample(definition, List.of(sentence));
        examples.add(example);

        return examples;
    }

    private Message getGenerateWordFromContentInitialSystemMessage(List<GenerateWordFromContentExample> examples) {

        StringBuilder message = new StringBuilder("你的任務是根據收到的英文內容，整理格式並回傳該英文單字相關的定義、搭配詞、例句。\\n" +
                "你會收到一段英文內容、裡面包含詞性、定義、例句，請根據這些資訊整理格式並回傳。\\n" +
                "以下為所有詞性 part_of_speech 的選項，回傳時請確保詞性為下列其中一種：[\"N\", \"PRON\", \"VT\", \"VI\", \"ADV\", \"ADJ\", \"PREP\", \"CONJ\", \"DET\", \"INTERJ\", \"NUM\", \"PHR\", \"ABBR\"]\n" +
                "請注意，請確保你生成的內容都與用戶提供的資訊相關聯，這是最重要的要求。\\n");

        message.append("以下是範例的輸入和輸出：");

        for (GenerateWordFromContentExample example : examples) {
            try{
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                message.append(objectMapper.writeValueAsString(example));
            } catch (Exception e) {
                message.append("Exception occurred while writing example: ").append(e.getMessage());
            }
        }

        message.append("請注意，以上僅為輸入與輸出範例。");

        return new SystemMessage(message.toString());
    }

    private Message getGenerateWordFromContentFormatExampleMessage(List<GenerateWordFromContentExample> examples) {

        CreateWordWithDetailRequest exampleOutput = examples.get(0).getExampleOutput();

        String exampleOutputString;

        try {
            exampleOutputString = objectMapper.writeValueAsString(exampleOutput);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert example output to JSON: " + e.getMessage(), e);
        }

        return new SystemMessage(
                "以下是一個範例的json格式回應：\n\n" +
                        exampleOutputString + "\n\n" +
                        "請注意，以上json回應僅為格式範例，實際生成內容請依照收到你收到的內容生成。\n\n"
        );
    }

    private List<GenerateWordFromContentExample> getGenerateWordFromContentExamples() {

        List<GenerateWordFromContentExample> examples = new ArrayList<>();

        GenerateWordFromContentExample generateExample1 = new GenerateWordFromContentExample();
        examples.add(generateExample1);

        GenerateWordFromContentRequest exampleInput1 = new GenerateWordFromContentRequest();
        exampleInput1.setContent("""
                lead
                verb
                
                B2 [T]
                to control a group of people, a country, or a situation
                領導，帶領，率領
                She was chosen to lead the team in the project.
                她被選在這個專案中帶領團隊。
                """);
        generateExample1.setExampleInput(exampleInput1);

        CreateWordWithDetailRequest exampleOutput1 = new CreateWordWithDetailRequest("lead");
        generateExample1.setExampleOutput(exampleOutput1);

        DefinitionDto definition1 = new DefinitionDto(exampleOutput1.getName(), "領導，帶領，率領", PartOfSpeech.VT);
        exampleOutput1.getDefinitions().add(definition1);

        CollocationDto collocation1 = new CollocationDto("lead the team", "帶領團隊");
        definition1.getCollocations().add(collocation1);

        SentenceDto sentence1 = new SentenceDto("She was chosen to lead the team in the project.", "她被選在這個專案中帶領團隊。");
        collocation1.getSentences().add(sentence1);


        GenerateWordFromContentExample generateExample2 = new GenerateWordFromContentExample();
        examples.add(generateExample2);

        GenerateWordFromContentRequest exampleInput2 = new GenerateWordFromContentRequest();
        exampleInput2.setContent("""
                lead
                noun
                
                B2 [ S ]
                a winning position during a race or other situation where people are competing
                領先，佔優
                The team took the lead in the second half of the game.
                這支球隊在比賽的下半場取得了領先。
                The runner had a lead of 10 seconds over the second place.
                這位跑者領先第二名10秒鐘。
                """);
        generateExample2.setExampleInput(exampleInput2);

        CreateWordWithDetailRequest exampleOutput2 = new CreateWordWithDetailRequest("lead");
        generateExample2.setExampleOutput(exampleOutput2);

        DefinitionDto definition2 = new DefinitionDto("lead", "領先，佔優", PartOfSpeech.N);
        exampleOutput2.getDefinitions().add(definition2);

        CollocationDto collocation2 = new CollocationDto("take the lead", "取得領先");
        definition2.getCollocations().add(collocation2);

        SentenceDto sentence2 = new SentenceDto("The team took the lead in the second half of the game.", "這支球隊在比賽的下半場取得了領先。");
        collocation2.getSentences().add(sentence2);

        CollocationDto collocation3 = new CollocationDto("a lead of ...", "領先...的差距");
        definition2.getCollocations().add(collocation3);

        SentenceDto sentence3 = new SentenceDto("The runner had a lead of 10 seconds over the second place.", "這位跑者領先第二名10秒鐘。");
        collocation3.getSentences().add(sentence3);

        return examples;
    }

    private String getGenerateCollocationsForDefinitionRequestString(GenerateCollocationsForDefinitionRequest request) {
//        try {
//            return objectMapper.writeValueAsString(request);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to convert request to JSON: " + e.getMessage(), e);
//        }

        return "單字名稱：" + request.getWordName() + "\n" +
                "詞性：" + request.getPartOfSpeech().getChinese() + "\n" +
                "中文含義：" + request.getMeaning() + "\n";
    }

    private String getGenerateSentencesForCollocationRequestString(GenerateSentencesForCollocationRequest request) {

        return "單字名稱：" + request.getWordName() + "\n" +
                "詞性：" + request.getPartOfSpeech().getChinese() + "\n" +
                "中文含義：" + request.getDefinitionMeaning() + "\n" +
                "搭配詞內容：" + request.getCollocationContent() + "\n" +
                "搭配詞含義：" + request.getCollocationMeaning() + "\n";
    }

    private String getGenerateWordFromContentRequestString(GenerateWordFromContentRequest request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert request to JSON: " + e.getMessage(), e);
        }
    }

    private String preProcessJson(String json) {
        if (json.startsWith("```json") && json.endsWith("```")) {
            return json.substring(7, json.length() - 3).trim();
        }
        return json;
    }
}
