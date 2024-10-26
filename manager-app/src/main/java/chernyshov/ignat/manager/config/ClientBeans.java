package chernyshov.ignat.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

import chernyshov.ignat.manager.client.RestClientProductsRestClient;

@Configuration
public class ClientBeans {
	
	@Bean
	public RestClientProductsRestClient productsRestClients(
			@Value("${selmag.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
			@Value("${selmag.services.catalogue.username:catalogue_service_user}") String catalogueUsername,
			@Value("${selmag.services.catalogue.password:password}") String cataloguePassword) {
		return new RestClientProductsRestClient(RestClient.builder()
				.baseUrl(catalogueBaseUri)
				.requestInterceptor(
						new BasicAuthenticationInterceptor(catalogueUsername, cataloguePassword))
				.build());
	}
}
