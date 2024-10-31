package chernyshov.ignat.customer.confiq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import chernyshov.ignat.customer.client.WebClientProductsClient;

@Configuration
public class ClientConfig {

	@Bean 
	WebClientProductsClient webClientProductsClient(
			@Value("${selmag.service.catalogue.uri:http://localhost:8081}") String catalogueBaseUrl
	) {
		return new WebClientProductsClient(WebClient.builder()
				.baseUrl(catalogueBaseUrl)
				.build());
	}
}
