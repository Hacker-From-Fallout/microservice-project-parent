package chernyshov.ignat.feedback.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import chernyshov.ignat.feedback.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductRepository extends ReactiveCrudRepository<FavouriteProduct, UUID> {
	
	Flux<FavouriteProduct> findAllByUserId(String userId);

	Mono<Void> deleteByProductIdAndUserId(int productId, String userId);

	Mono<FavouriteProduct> findByProductIdAndUserId(int productId, String userId);
}
