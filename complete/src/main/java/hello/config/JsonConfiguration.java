package hello.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import hello.model.BillingStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JsonConfiguration {
    /**
     * JSON<->Enum
     * @return {@link ObjectMapper}
     */
    @Bean
    @Primary
    public ObjectMapper jsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(generateFlagModule());
        return mapper;
    }

    private Module generateFlagModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(BillingStatus.class, new BillingStatusDeserializer());
        return simpleModule;
    }
}