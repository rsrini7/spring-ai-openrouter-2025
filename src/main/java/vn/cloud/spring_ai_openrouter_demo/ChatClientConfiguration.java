package vn.cloud.spring_ai_openrouter_demo;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    // @Bean
    // public ChatClient chatClient(ChatClient.Builder builder) {
    //     return builder.build();
    // }

    @Bean
    public VectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        System.out.println("INFO: Initializing VectorStore with EmbeddingModel: " + embeddingModel.getClass().getName());
        
        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build(); // Corrected instantiation
        List<Document> documents = List.of(
            new Document("The Spring AI project aims to simplify the development of applications that incorporate artificial intelligence functionality without unnecessary complexity."),
            new Document("Spring AI provides abstractions like ChatClient and EmbeddingModel that are portable across LLM providers such as OpenAI, Azure OpenAI, HuggingFace, Ollama, and Bedrock."),
            new Document("Key features of Spring AI include support for synchronous and streaming Chat & Embedding APIs, Prompt Templating, Output Parsers, Model specific features (e.g. function calling, JSON output), and an ETL Framework for Data Engineering."),
            new Document("The capital of France is Paris. Paris is a major European city and a global center for art, fashion, gastronomy and culture. Its 19th-century cityscape is crisscrossed by wide boulevards and the River Seine."),
            new Document("Mars is the fourth planet from the Sun and the second-smallest planet in the Solar System, being larger than only Mercury. In English, Mars carries the name of the Roman god of war and is often referred to as the \"Red Planet\"."),
            new Document("The first programmable computer was the Z3, created by Konrad Zuse in Germany in 1941.")
        );
        store.add(documents);
        System.out.println("INFO: In-memory VectorStore initialized with " + documents.size() + " documents.");
        return store;
    }

    @Bean
    public ChatClient ragChatClient(ChatModel chatModel, VectorStore vectorStore) {
        System.out.println("INFO: Initializing ChatClient with ChatModel: " + chatModel.getClass().getName());
        return ChatClient.builder(chatModel)
            .defaultAdvisors(RetrievalAugmentationAdvisor.builder() // Uses updated import
                .documentRetriever(VectorStoreDocumentRetriever.builder() // Uses updated import
                    .vectorStore(vectorStore)
                    .similarityThreshold(0.70)
                    .topK(3)
                    .build())
                .build())
            .build();
    }
}
