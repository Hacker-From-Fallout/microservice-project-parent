package chernyshov.ignat.manager.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.stereotype.Repository;

import chernyshov.ignat.manager.entity.Product;

@Repository
public class InMemoryProductRepository implements ProductRepository {
	
	private final List<Product> products = Collections.synchronizedList(new LinkedList<>());
	
	@Override
	public List<Product> findAll() {
		return Collections.unmodifiableList(this.products);
	}

	@Override
	public Product save(Product product) {
		product.setId(this.products.stream()
				.max(Comparator.comparingInt(Product::getId))
				.map(Product::getId)
				.orElse(0) + 1);
		this.products.add(product);
		return product;
	}

	@Override
	public Optional<Product> findById(Integer productId) {
		return this.products.stream()
				.filter(product -> Objects.equals(productId, product.getId()))
				.findFirst();
	}

	@Override
	public void deleteById(Integer id) {
		this.products.removeIf(product -> Objects.equals(id, product.getId()));
	}
	
}
