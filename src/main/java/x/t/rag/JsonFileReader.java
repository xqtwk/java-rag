package x.t.rag;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JsonFileReader {
    private final VectorStore vectorStore;

    @Value("classpath:sportsdata.json")
    private Resource jsonResource;

    public JsonFileReader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void init() {
        try (InputStream inputStream = jsonResource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> data = objectMapper.readValue(inputStream, new TypeReference<>() {});

            List<Document> documents = data.stream()
                    .map(entry -> new Document(entry.get("content"), Map.of("title", entry.get("title"))))
                    .collect(Collectors.toList());
            vectorStore.accept(documents);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read and parse JSON file", e);
        }
    }
}
