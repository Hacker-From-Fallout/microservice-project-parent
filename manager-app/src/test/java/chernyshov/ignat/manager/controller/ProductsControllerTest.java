package chernyshov.ignat.manager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import chernyshov.ignat.manager.client.BadRequestException;
import chernyshov.ignat.manager.client.ProductsRestClient;
import chernyshov.ignat.manager.controller.payload.NewProductPayload;
import chernyshov.ignat.manager.entity.Product;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты ProductsController")
class ProductsControllerTest {

	@Mock
	ProductsRestClient productsRestClients;
	
	@InjectMocks
	ProductsController controller;

	@Test 
	@DisplayName("createProduct создаст новый товар и перенаправит на страницу товара")
	void createProduct_RequestIsValid_ReturnsRedirectionToProductPage() {
		// given
		var payload = new NewProductPayload("Новй товар", "Описнаие нового товара");
		var model = new ConcurrentModel();
		
		doReturn(new Product(1, "Новй товар", "Описнаие нового товара"))
				.when(this.productsRestClients)
				.createProduct("Новй товар", "Описнаие нового товара");
		
		// when
		var result = this.controller.createProduct(payload, model);
		
		// then
		assertEquals("redirect:/catalogue/products/1", result);
		
		verify(this.productsRestClients).createProduct("Новй товар", "Описнаие нового товара");
		verifyNoMoreInteractions(this.productsRestClients);
	}
	
	@Test 
	@DisplayName("createProduct вернет страницу с ошибками, если запрос не валиден")
	void createProduct_RequestIsInvalid_ReturnsProductFormWithErrors() {
		// given
		var payload = new NewProductPayload("  ", null);
		var model = new ConcurrentModel();
		
		doThrow(new BadRequestException(List.of("Ошибка 1", "Ошибка 2")))
				.when(this.productsRestClients)
				.createProduct("  ", null);
		
		// when
		var result = this.controller.createProduct(payload, model);
		
		// then
		assertEquals("catalogue/products/new_product", result);
		assertEquals(payload, model.getAttribute("payload"));
		assertEquals(List.of("Ошибка 1", "Ошибка 2"), model.getAttribute("errors"));
		
		verify(this.productsRestClients).createProduct("  ", null);
		verifyNoMoreInteractions(this.productsRestClients);
	}
}
