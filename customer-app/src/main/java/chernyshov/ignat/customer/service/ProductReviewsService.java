package chernyshov.ignat.customer.service;

import chernyshov.ignat.customer.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewsService {

	Mono<ProductReview> createProductReview(int ProductId, int rating, String review);
	
	Flux<ProductReview> findProductReviewsByProduct(int productId);
}
