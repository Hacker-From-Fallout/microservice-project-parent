package chernyshov.ignat.manager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import chernyshov.ignat.manager.entity.SelmagUser;

public interface SelmagUserRepository extends CrudRepository<SelmagUser, Integer> {
	
	Optional<SelmagUser> findByUsername(String username);
}
