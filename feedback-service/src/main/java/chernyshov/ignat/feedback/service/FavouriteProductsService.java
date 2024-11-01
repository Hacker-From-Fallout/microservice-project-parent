package chernyshov.ignat.feedback.service;

import chernyshov.ignat.feedback.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductsService {

	Mono<FavouriteProduct> addProductToFavourites(int productId);
	
	Mono<Void> removeProductFromFavourites(int productId);
	 
	Mono<FavouriteProduct> findFavouriteProductByProduct(int productid);
	
	Flux<FavouriteProduct> findFavouriteProducts();
}
