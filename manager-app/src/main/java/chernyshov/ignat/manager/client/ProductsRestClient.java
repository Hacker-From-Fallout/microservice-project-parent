package chernyshov.ignat.manager.client;

import java.util.List;
import java.util.Optional;

import chernyshov.ignat.manager.entity.Product;

public interface ProductsRestClient {

    List<Product> findAllProducts(String filter);

    Product createProduct(String title, String details);

    Optional<Product> findProduct(int productId);

    void updateProduct(int productId, String title, String details);

    void deleteProduct(int productId);
}
