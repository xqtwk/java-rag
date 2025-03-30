package x.t.rag;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import x.t.rag.Indexing.AsyncIndexingService;

@RestController
@RequestMapping("/api/v1/index")
@RequiredArgsConstructor
public class IndexController {

    private final AsyncIndexingService asyncIndexingService;

    @Value("${data.files.path}")
    private String dataFilesPath;

    @PostMapping
    public String triggerIndexing() {
        try {
            asyncIndexingService.indexAllFilesAsync(dataFilesPath);
            return "Async reindexing finished.";
        } catch (Exception e) {
            return "Failed to start reindexing: " + e.getMessage();
        }
    }
}

