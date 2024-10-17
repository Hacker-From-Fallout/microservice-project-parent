package chernyshov.ignat.manager.repository;

import java.util.List;
import java.util.Optional;

import chernyshov.ignat.manager.entity.Product;

public interface ProductRepository {

	List<Product> findAll();

	Product save(Product product);

	Optional<Product> findById(Integer productId);

	void deleteById(Integer id);

}
