package chernyshov.ignat.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import chernyshov.ignat.customer.client.ProductsClient;
import chernyshov.ignat.customer.entity.FavouriteProduct;
import chernyshov.ignat.customer.service.FavouriteProductsService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products")
public class ProductsController {

	private final ProductsClient productsClient;
	
	private final FavouriteProductsService favouriteProductsService;
	
	@GetMapping("list")
	public Mono<String> getProductsListPage(Model model,
											@RequestParam(name = "filter", required = false) String filter ) {
		model.addAttribute("filter", filter);
		return this.productsClient.findAllProducts(filter)
				.collectList()
				.doOnNext(products -> model.addAttribute("products", products))
				.thenReturn("customer/products/list");
	}
	
	@GetMapping("favourites")
    public Mono<String> getFavouriteProductsPage(Model model,
                                                 @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return this.favouriteProductsService.findFavouriteProducts()
                .map(FavouriteProduct::getProductId)
                .collectList()
                .flatMap(favouriteProducts -> this.productsClient.findAllProducts(filter)
                        .filter(product -> favouriteProducts.contains(product.id()))
                        .collectList()
                        .doOnNext(products -> model.addAttribute("products", products)))
                .thenReturn("customer/products/favourites");
    }
}
