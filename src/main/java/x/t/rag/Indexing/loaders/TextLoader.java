package x.t.rag.Indexing.loaders;

import org.springframework.stereotype.Component;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TextLoader implements DocumentLoader {
    public List<Document> load(Resource resource) {
        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines().map(Document::new).collect(Collectors.toList());
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}