package chernyshov.ignat.customer.controller;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import chernyshov.ignat.customer.client.ProductsClient;
import chernyshov.ignat.customer.controller.payload.NewProductReviewPayload;
import chernyshov.ignat.customer.entity.Product;
import chernyshov.ignat.customer.service.FavouriteProductsService;
import chernyshov.ignat.customer.service.ProductReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products/{productId:\\d+}")
public class ProductController {

	private final ProductsClient productsClient;
	
	private final FavouriteProductsService favouriteProductsService;
	
	private final ProductReviewsService productReviewsService;
	
	@ModelAttribute(name = "product", binding = false)
	public Mono<Product> loadProduct(@PathVariable("productId") int id) {
		return this.productsClient.findProduct(id)
				.switchIfEmpty(Mono.error(new NoSuchElementException("customer.products.error.not_found")));
	}
	
	@GetMapping()
	public Mono<String> getProductPage(@ModelAttribute(name = "product", binding = false) 
									   Mono<Product> productMono, @PathVariable("productId") int id, 
	                                   Model model) {
	    model.addAttribute("inFavourite", false);

	    // Получаем отзывы о продукте
	    return this.productReviewsService.findProductReviewsByProduct(id)
	            .collectList()
	            .doOnNext(productReviews -> model.addAttribute("reviews", productReviews)) // Добавляем отзывы в модель
	            .then(this.favouriteProductsService.findFavouriteProductByProduct(id)
	                    .doOnNext(favouriteProduct -> model.addAttribute("inFavourite", true)) // Обновляем статус избранного
	                    .switchIfEmpty(Mono.defer(() -> { // Обработка случая, когда продукт не найден
	                        model.addAttribute("inFavourite", false);
	                        return Mono.empty();
	                    })))
	            .then(productMono // Здесь мы используем загруженный продукт
	                    .doOnNext(product -> model.addAttribute("product", product)) // Добавляем продукт в модель
	            )
	            .thenReturn("customer/products/product"); // Возвращаем представление
	}
	
	@PostMapping("add-to-favourites")
    public Mono<String> addProductToFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favouriteProductsService.addProductToFavourites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("remove-from-favourites")
    public Mono<String> removeProductFromFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favouriteProductsService.removeProductFromFavourites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }
    
    @PostMapping("create-review")
    public Mono<String> createReview(
            @PathVariable("productId") int id, 
            @Valid NewProductReviewPayload payload, 
            BindingResult bindingResult, 
            Model model, 
            @ModelAttribute(name = "product", binding = false) Mono<Product> productMono) {
        
        // Обработка ошибок валидации
        if (bindingResult.hasErrors()) {
            model.addAttribute("inFavourite", false);
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());

            // Добавляем продукт в модель, если он загружен
            return productMono
                    .doOnNext(product -> model.addAttribute("product", product)) // Добавляем продукт в модель
                    .then(this.favouriteProductsService.findFavouriteProductByProduct(id)
                            .doOnNext(favouriteProduct -> model.addAttribute("inFavourite", true))
                            .thenReturn("customer/products/product"));
        } else {
            // Создание отзыва продукта
            return this.productReviewsService.createProductReview(id, payload.rating(), payload.review())
                    .thenReturn("redirect:/customer/products/%d".formatted(id));
        }
    }
    
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }
}
