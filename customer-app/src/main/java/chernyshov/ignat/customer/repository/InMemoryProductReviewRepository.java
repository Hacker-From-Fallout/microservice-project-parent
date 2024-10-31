package chernyshov.ignat.customer.repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import chernyshov.ignat.customer.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class InMemoryProductReviewRepository implements ProductReviewRepository {

    private final List<ProductReview> productReviews = Collections.synchronizedList(new LinkedList<>());

    @Override
    public Mono<ProductReview> save(ProductReview productReview) {
        this.productReviews.add(productReview);
        return Mono.just(productReview);
    }

    @Override
    public Flux<ProductReview> findAllByProductId(int productId) {
        return Flux.fromIterable(this.productReviews)
                .filter(productReview -> productReview.getProductId() == productId);
    }
}
