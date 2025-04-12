package vn.cloud.spring_ai_openrouter_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAiOpenrouterDemoApplication {

	public static void main(String[] args) {
		System.setProperty("jdk.httpclient.HttpClient.log", "all");
		SpringApplication.run(SpringAiOpenrouterDemoApplication.class, args);
	}

}
