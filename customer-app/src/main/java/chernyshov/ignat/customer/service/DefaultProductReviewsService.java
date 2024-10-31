package chernyshov.ignat.customer.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import chernyshov.ignat.customer.entity.ProductReview;
import chernyshov.ignat.customer.repository.ProductReviewRepository;
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
