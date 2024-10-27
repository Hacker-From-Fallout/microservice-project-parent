package chernyshov.ignat.manager.controller;

import java.security.Principal;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String getProductsList(Model model, @RequestParam(name = "filter", required = false) String filter) {
		model.addAttribute("products", this.productsRestClient.findAllProducts(filter));
		model.addAttribute("filter", filter);
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
