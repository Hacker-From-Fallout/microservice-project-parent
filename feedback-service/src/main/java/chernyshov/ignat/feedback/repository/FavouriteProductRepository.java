package chernyshov.ignat.feedback.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import chernyshov.ignat.feedback.entity.FavouriteProduct;
import reactor.core.publisher.Mono;

public interface FavouriteProductRepository extends ReactiveCrudRepository<FavouriteProduct, UUID> {

    Mono<Void> deleteByProductId(int productId);

    Mono<FavouriteProduct> findByProductId(int productId);
}
