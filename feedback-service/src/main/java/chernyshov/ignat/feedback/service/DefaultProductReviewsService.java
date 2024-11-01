package chernyshov.ignat.feedback.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import chernyshov.ignat.feedback.entity.ProductReview;
import chernyshov.ignat.feedback.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultProductReviewsService implements ProductReviewsService {

	private final ProductReviewRepository productReviewRepository;
	
	@Override
	public Mono<ProductReview> createProductReview(int productId, int rating, String review) {
		return this.productReviewRepository.save(
				new ProductReview(UUID.randomUUID(), productId, rating, review));
	}

	@Override
	public Flux<ProductReview> findProductReviewsByProduct(int productId) {
		return this.productReviewRepository.findAllByProductId(productId);
	}

}
