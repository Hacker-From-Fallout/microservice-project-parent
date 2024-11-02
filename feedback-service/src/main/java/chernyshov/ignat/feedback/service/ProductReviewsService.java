package chernyshov.ignat.feedback.service;

import chernyshov.ignat.feedback.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewsService {

	Mono<ProductReview> createProductReview(int ProductId, int rating, String review, String UserId);
	
	Flux<ProductReview> findProductReviewsByProduct(int productId);
}
