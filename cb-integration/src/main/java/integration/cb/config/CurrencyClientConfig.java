package integration.cb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "integration.client")
public class CurrencyClientConfig {

    private String cbUrl;
    private String paramName;
    private String datePattern;

}