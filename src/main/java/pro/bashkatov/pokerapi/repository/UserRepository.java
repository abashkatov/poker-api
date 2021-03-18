package pro.bashkatov.pokerapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pro.bashkatov.pokerapi.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Long countByUsername(String username);
    User findUserByUsername(String username);
}
