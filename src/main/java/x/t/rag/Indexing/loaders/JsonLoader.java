package x.t.rag.Indexing.loaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JsonLoader implements DocumentLoader {
    public List<Document> load(Resource resource) {
        try (var in = resource.getInputStream()) {
            List<Map<String, String>> data = new ObjectMapper().readValue(in, new TypeReference<>() {});
            return data.stream()
                    .map(entry -> new Document(entry.get("content"), Map.of("title", entry.get("title"))))
                    .collect(Collectors.toList());
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}