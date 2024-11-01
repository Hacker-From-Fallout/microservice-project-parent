package chernyshov.ignat.feedback.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import chernyshov.ignat.feedback.entity.ProductReview;
import reactor.core.publisher.Flux;

public interface ProductReviewRepository extends ReactiveCrudRepository<ProductReview, UUID> {
	
	Flux<ProductReview> findAllByProductId(int productId);
}
