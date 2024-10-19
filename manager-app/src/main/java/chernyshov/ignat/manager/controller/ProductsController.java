package chernyshov.ignat.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import chernyshov.ignat.manager.client.BadRequestException;
import chernyshov.ignat.manager.client.ProductsRestClient;
import chernyshov.ignat.manager.controller.payload.NewProductPayload;
import chernyshov.ignat.manager.entity.Product;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

	private final ProductsRestClient productsRestClient;
	
	@GetMapping("list")
	public String getProductsList(Model model) {
		model.addAttribute("products", this.productsRestClient.findAllProducts());
		return "catalogue/products/list";
	}
	
	@GetMapping("create")
	public String getNewProductPage() {
		return "catalogue/products/new_product";
	}
	
	@PostMapping("create")
	public String createProduct(NewProductPayload payload,
								Model model) {
		try {
            Product product = this.productsRestClient.createProduct(payload.title(), payload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/products/new_product";
        }
	}	
}
