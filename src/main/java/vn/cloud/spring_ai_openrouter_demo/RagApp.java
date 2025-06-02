package vn.cloud.spring_ai_openrouter_demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class RagApp {

    public static void main(String[] args) {
        String apiKey = System.getenv("OPENROUTER_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("INFO: OPENROUTER_API_KEY environment variable not set. " +
                               "Ensure it's provided in application.properties or via -Dspring.ai.openai.api-key.");
            //System.exit(0);
        }
        System.out.println("INFO: Attempting to download ONNX model for embeddings if not cached. This might take a moment on the first run...");
        SpringApplication.run(RagApp.class, args);
    }
   

    @Component
    static class RagCommandLineRunner implements CommandLineRunner {

        private final ChatClient ragChatClient;
        private final VectorStore vectorStore;

        public RagCommandLineRunner(@Qualifier("ragChatClient") ChatClient ragChatClient, VectorStore vectorStore) {
            this.ragChatClient = ragChatClient;
            this.vectorStore = vectorStore;
        }

        @Override
        public void run(String... args) throws Exception {
            System.out.println("\n=== Basic RAG Application with Spring AI ===");
            System.out.println("Chat Model via OpenRouter, Embeddings via local HuggingFace Transformers.");
            System.out.println("Using VectorStore: " + vectorStore.getClass().getSimpleName());
            System.out.println("Using ChatClient configured with RAG advisor.");
            System.out.println("Ask questions about Spring AI, Paris, Mars, or early computers.");
            System.out.println("Example: 'What is Spring AI?' or 'Tell me about Paris.'");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\nYour question (or type 'exit' to quit): ");
                String question = scanner.nextLine();
                if ("exit".equalsIgnoreCase(question.trim())) {
                    break;
                }
                if (question.trim().isEmpty()) {
                    continue;
                }

                System.out.println("INFO: Searching for relevant documents using local embeddings...");
                List<Document> similarDocs = vectorStore.similaritySearch(
                    SearchRequest.builder().query(question).topK(3).similarityThreshold(0.70).build()
                );

                if (similarDocs.isEmpty()) {
                    System.out.println("INFO: No highly similar documents found in the vector store for your query to form context.");
                } else {
                    System.out.println("INFO: Found " + similarDocs.size() + " document(s) to use as context:");
                    similarDocs.forEach(doc ->
                        System.out.println("  - " + doc.getText().substring(0, Math.min(doc.getText().length(), 80)) + "...") // Corrected: doc.getContent()
                    );
                }

                System.out.println("INFO: Sending question to AI (OpenRouter) with retrieved context...");
                ChatResponse response = ragChatClient.prompt() 
                        .user(question)
                        .call() // Call on PromptSender returns a ResponseSpec
                        .chatResponse(); // Get ChatResponse from ResponseSpec

                System.out.println("\nAI Response:");
                System.out.println(response.getResult().getOutput().getText());
            }
            scanner.close();
            System.out.println("\nExiting RAG application.");
        }
    }
}