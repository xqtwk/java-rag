package x.t.rag;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import x.t.rag.Indexing.AsyncIndexingService;

@RestController
@RequestMapping("/api/v1/index")
@RequiredArgsConstructor
public class IndexController {

    private final AsyncIndexingService asyncIndexingService;

    @PostMapping
    public String triggerIndexing() {
        try {
            asyncIndexingService.indexAllFilesAsync("classpath:data/*.*");
            return "Async reindexing finished.";
        } catch (Exception e) {
            return "Failed to start reindexing: " + e.getMessage();
        }
    }
}

