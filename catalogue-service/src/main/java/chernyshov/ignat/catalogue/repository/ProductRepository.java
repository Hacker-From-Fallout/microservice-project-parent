package chernyshov.ignat.catalogue.repository;

import org.springframework.data.repository.CrudRepository;

import chernyshov.ignat.catalogue.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	//@Query(value = "select p from Product p where p.title ilike :filter")	
	//@Query(value = "SELECT * FROM catalogue.t_product WHERE c_title LIKE :filter", nativeQuery = true)
	Iterable<Product> findAllByTitleLikeIgnoreCase(String filter);
}
