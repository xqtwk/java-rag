package x.t.rag.Indexing.loaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import x.t.rag.Indexing.AsyncIndexingService;

@Component
public class InitialDataLoader implements CommandLineRunner {
    private final AsyncIndexingService indexingService;

    public InitialDataLoader(AsyncIndexingService indexingService) {
        this.indexingService = indexingService;
    }

    @Override
    public void run(String... args) throws Exception {
        indexingService.indexAllFilesAsync("classpath:data/*.*");
        Thread.sleep(200000);
    }
}