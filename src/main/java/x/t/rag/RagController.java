package x.t.rag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {

    private final ChatClient aiClient;
    private final VectorStore vectorStore;

    public RagController(ChatClient aiClient, VectorStore vectorStore) {
        this.aiClient = aiClient;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/robobrain/rag")
    public ResponseEntity<String> generateAnswer(@RequestParam String query) {
        List<Document> similarDocuments = vectorStore.similaritySearch(query);
        String information = similarDocuments.stream()
                .map(Document::getFormattedContent)
                .collect(Collectors.joining(System.lineSeparator()));

        var systemPromptTemplate = new SystemPromptTemplate(
                """
                            You are a helpful assistant.
                            Use only the following information to answer the question.
                            Do not use any other information. If you do not know, simply answer: Unknown.

                            {information}
                        """);
        var userPromptTemplate = new PromptTemplate("{query}");

        Prompt prompt = new Prompt(List.of(
                systemPromptTemplate.createMessage(Map.of("information", information)),
                userPromptTemplate.createMessage(Map.of("query", query))
        ));

        String result = aiClient.prompt(prompt).call().content();
        return ResponseEntity.ok(result);
    }
}

