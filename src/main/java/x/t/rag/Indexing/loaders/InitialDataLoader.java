package x.t.rag.Indexing.loaders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import x.t.rag.Indexing.AsyncIndexingService;

@Component
public class InitialDataLoader implements CommandLineRunner {
    private final AsyncIndexingService indexingService;

    @Value("${data.files.path}")
    private String dataFilesPath;

    public InitialDataLoader(AsyncIndexingService indexingService) {
        this.indexingService = indexingService;
    }

    @Override
    public void run(String... args) throws Exception {
        indexingService.indexAllFilesAsync(dataFilesPath);
        Thread.sleep(200000);
    }
}