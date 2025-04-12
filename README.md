# Spring AI - OpenRouter - Google Gemini 2.5 Pro & Llama 4 Maverick  

A Spring Boot application demonstrating integration with multiple AI models through OpenRouter API using Spring AI framework.

## Overview

This project showcases how to build a simple chat application that can:
- Connect to various AI models via OpenRouter
- Process and handle multimedia files in prompts
- Provide a RESTful API endpoint for interacting with AI models

## Technologies Used

- Java 21
- Spring Boot 3.4.4
- Spring AI 1.0.0-M6
- OpenRouter API (providing access to models like Meta Llama 4 Maverick and Google Gemini 2.5 Pro)

## Project Structure

```
└── src
    ├── main
    │   ├── java
    │   │   └── vn/cloud/spring_ai_openrouter_demo
    │   │       ├── ChatClientConfiguration.java - Configures the ChatClient bean
    │   │       ├── ChatController.java - REST API endpoint for chat interactions
    │   │       ├── JacksonConfiguration.java - Jackson JSON parser configuration
    │   │       └── SpringAiOpenrouterDemoApplication.java - Main application class
    │   └── resources
    │       └── application.yml - Application configuration properties
    └── test
        └── java
            └── vn/cloud/spring_ai_openrouter_demo
                └── SpringAiOpenrouterDemoApplicationTests.java - Basic context load test
```

## Features

- **REST API Endpoint**: Send questions and files to AI models via a POST request
- **Multi-Model Support**: Configure different AI models through application.yml
- **Multimedia Support**: Upload and process files alongside text prompts
- **High File Size Limits**: Configured to handle files up to 20MB

## Setup and Configuration

### Prerequisites

- Java 21 or higher
- Gradle 8.x
- OpenRouter API key

### Configuration

1. Open `src/main/resources/application.yml`
2. Replace `{ OPEN_ROUTER_API_KEY }` with your actual OpenRouter API key
3. Optionally change the model configuration:
   ```yaml
   model: google/gemini-2.5-pro-exp-03-25:free
   # or uncomment to use:
   # model: meta-llama/llama-4-maverick:free
   ```

### Building the Project

```bash
./gradlew clean build
```

### Running the Application

```bash
./gradlew bootRun
```

Or after building:

```bash
java -jar build/libs/spring-ai-openrouter-demo-0.0.1-SNAPSHOT.jar
```

## API Usage

### Chat Endpoint

Send a POST request to `/chat` with:

- `question`: The text prompt to send to the AI model
- `files`: Optional files to include with the prompt (supports multiple files)

#### Example using curl:

```bash
curl -X POST http://localhost:8080/chat \
  -F "question=Describe what you see in this image" \
  -F "files=@/path/to/your/image.jpg"
```

#### Example using Postman:

1. Set request type to POST
2. URL: http://localhost:8080/chat
3. Body: form-data
   - Key: question, Value: "Describe what you see in this image"
   - Key: files, Value: [select file], Type: File

## Development

This project uses standard Spring Boot development practices. You can import it into any IDE that supports Gradle projects.

## License

This project is open source and available under the [MIT License](LICENSE).

## Additional Resources

- [**OpenRouter Gemini 2.5 Pro Experimental**](https://openrouter.ai/google/gemini-2.5-pro-exp-03-25:free)
- [**OpenRouter Llama 4 Maverick**](https://openrouter.ai/meta-llama/llama-4-maverick:free)
- [**OpenAI API Reference**](https://platform.openai.com/docs/api-reference/chat)
