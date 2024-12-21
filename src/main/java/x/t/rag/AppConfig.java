package x.t.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${spring.ai.openai.api-key}")
    private String OPENAI_API_KEY;

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    public OpenAiApi openAiApi() {
        return new OpenAiApi("https://api.openai.com", OPENAI_API_KEY);
    }
    @Bean
    public ChatClient chatClient(OpenAiApi openAiApi) {
        OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi);
        return ChatClient.create(chatModel);
    }
}