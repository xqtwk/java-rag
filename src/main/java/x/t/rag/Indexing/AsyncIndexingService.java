package x.t.rag.Indexing;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import x.t.rag.Indexing.loaders.DocumentLoader;
import x.t.rag.Indexing.loaders.LoaderFactory;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncIndexingService {
    private final VectorStore vectorStore;
    private final LoaderFactory loaderFactory;

    public AsyncIndexingService(VectorStore vectorStore, LoaderFactory loaderFactory) {
        this.vectorStore = vectorStore;
        this.loaderFactory = loaderFactory;
    }

    @Async
    public CompletableFuture<Void> indexFile(Resource resource, String fileType) {
        DocumentLoader loader = loaderFactory.getLoader(fileType);
        vectorStore.accept(loader.load(resource));
        return CompletableFuture.completedFuture(null);
    }

    public void indexAllFilesAsync(String pathPattern) throws Exception {
        var resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(pathPattern);

        for (Resource resource : resources) {
            String filename = resource.getFilename().toLowerCase();
            String fileType = filename.substring(filename.lastIndexOf('.') + 1);
            indexFile(resource, fileType);
            System.out.println("Started async indexing: " + filename);
        }
    }
}