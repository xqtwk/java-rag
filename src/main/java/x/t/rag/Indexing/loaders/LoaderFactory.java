package x.t.rag.Indexing.loaders;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LoaderFactory {
    private final Map<String, DocumentLoader> loaders;

    public LoaderFactory(List<DocumentLoader> loaderList) {
        loaders = Map.of(
                "json", loaderList.stream().filter(l -> l instanceof JsonLoader).findFirst().get(),
                "pdf", loaderList.stream().filter(l -> l instanceof PdfLoader).findFirst().get(),
                "md", loaderList.stream().filter(l -> l instanceof MarkdownLoader).findFirst().get(),
                "txt", loaderList.stream().filter(l -> l instanceof TextLoader).findFirst().get()
        );
    }

    public DocumentLoader getLoader(String type) {
        return loaders.get(type.toLowerCase());
    }
}