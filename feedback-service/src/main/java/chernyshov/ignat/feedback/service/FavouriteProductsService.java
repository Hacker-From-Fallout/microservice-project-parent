package chernyshov.ignat.feedback.service;

import chernyshov.ignat.feedback.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductsService {

	Mono<FavouriteProduct> addProductToFavourites(int productId, String userId);
	
	Mono<Void> removeProductFromFavourites(int productId, String userId);
	 
	Mono<FavouriteProduct> findFavouriteProductByProduct(int productid, String userId);
	
	Flux<FavouriteProduct> findFavouriteProducts(String userId);
}
