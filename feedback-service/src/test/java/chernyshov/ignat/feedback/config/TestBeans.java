package chernyshov.ignat.feedback.config;

import static org.mockito.Mockito.mock;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.testcontainers.containers.MongoDBContainer;

@Configuration
public class TestBeans {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ServiceConnection
    public MongoDBContainer mongoDBContainer() {
        return new MongoDBContainer("mongo:7");
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return mock();
    }

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
        return mock();
    }
}
