package vn.cloud.spring_ai_openrouter_demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.model.Media;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping
    public String ask(@RequestParam("files") List<MultipartFile> files, @RequestParam String question) {
        List<Media> mediaList = files.stream()
                .map(file -> new Media(MimeType.valueOf(Objects.requireNonNull(file.getContentType())), file.getResource()))
                .toList();

        return this.chatClient.prompt()
                .messages(new UserMessage(question, mediaList))
                .call()
                .content();
    }
}
