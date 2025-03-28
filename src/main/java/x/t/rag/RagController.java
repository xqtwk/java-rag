package x.t.rag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import x.t.rag.dto.QueryRequest;
import x.t.rag.dto.QueryResponse;

@RestController
@RequiredArgsConstructor
public class RagController {
    private final ChatClient aiClient;
    private final VectorStore vectorStore;

    @PostMapping("api/v1/rag/query")
    public ResponseEntity<QueryResponse> queryRag(@RequestBody QueryRequest request) {
        String query = request.query();
        List<Document> similarDocuments = vectorStore.similaritySearch(query);
        String information = similarDocuments.stream()
                .map(Document::getFormattedContent)
                .collect(Collectors.joining(System.lineSeparator()));

        var systemPromptTemplate = new SystemPromptTemplate(
                """
                You are a helpful assistant. Answer using only following information to answer the question.
                If unsure, say 'Unknown'.
            
                {information}
                """);
        var userPromptTemplate = new PromptTemplate("{query}");

        Prompt prompt = new Prompt(List.of(
                systemPromptTemplate.createMessage(Map.of("information", information)),
                userPromptTemplate.createMessage(Map.of("query", query))
        ));

        System.out.println(prompt);
        QueryResponse response = new QueryResponse(aiClient.prompt(prompt).call().content());
        return ResponseEntity.ok(response);
    }
}

