package com.vincennlin.collofulbackend.service.ai.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.collofulbackend.payload.word.PartOfSpeech;
import com.vincennlin.collofulbackend.payload.word.dto.CollocationDto;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import com.vincennlin.collofulbackend.payload.word.dto.SentenceDto;
import com.vincennlin.collofulbackend.payload.word.request.CreateCollocationsForDefinitionRequest;
import com.vincennlin.collofulbackend.payload.word.request.DefinitionCollocationExample;
import com.vincennlin.collofulbackend.payload.word.request.GenerateCollocationsForDefinitionRequest;
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

        List<DefinitionCollocationExample> examples = getExamples();

        List<Message> messages = List.of(
                getInitialSystemMessage(examples),
                getFormatExampleMessage(examples),
                new UserMessage(getRequestString(request))
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

    private Message getInitialSystemMessage(List<DefinitionCollocationExample> examples) {

        StringBuilder message = new StringBuilder("你的任務是根據收到的單字定義，生成與該單字相關的搭配詞。\\n" +
                "你會收到一個英文單字的名稱、含義和詞性，請根據這些資訊生成搭配詞。\\n" +
                "每個搭配詞應包含其含義和至少一個例句。\\n" +
                "請確保搭配詞的含義和例句與單字的定義相關聯。\\n" +
                "每個單字請生成一至三個搭配詞。\\n");

        message.append("以下是範例的輸入和輸出：");

        for (DefinitionCollocationExample example : examples) {
            try{
                message.append(objectMapper.writeValueAsString(example));
            } catch (Exception e) {
                message.append("Exception occurred while writing example: ").append(e.getMessage());
            }
        }

        message.append("請注意，以上json回應僅為格式範例，實際生成的搭配詞請根據收到的單字生成。");

        return new SystemMessage(message.toString());
    }

    private Message getFormatExampleMessage(List<DefinitionCollocationExample> examples) {

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

    private List<DefinitionCollocationExample> getExamples() {

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

    private String getRequestString(GenerateCollocationsForDefinitionRequest request) {
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
