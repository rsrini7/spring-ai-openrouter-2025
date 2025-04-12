package vn.cloud.spring_ai_openrouter_demo;

import com.fasterxml.jackson.core.StreamReadConstraints;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {
    static {
        StreamReadConstraints.overrideDefaultStreamReadConstraints(
                StreamReadConstraints.builder().maxStringLength(100_000_000).build()
        );
    }
}
