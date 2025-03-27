package x.t.rag.Indexing.loaders;

import org.springframework.stereotype.Component;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarkdownLoader implements DocumentLoader {
    public List<Document> load(Resource resource) {
        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String content = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            return List.of(new Document(content));
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}