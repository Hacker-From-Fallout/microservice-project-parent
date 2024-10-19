package chernyshov.ignat.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import chernyshov.ignat.manager.client.RestClientProductsRestClient;

@Configuration
public class ClientBeans {
	
	@Bean
	public RestClientProductsRestClient productsRestClients(
			@Value("${selmag.service.catalogue.uri:http://localhost:8081}") String catalogueBaseUri) {
		return new RestClientProductsRestClient(RestClient.builder()
				.baseUrl(catalogueBaseUri)
				.build());
	}
}
