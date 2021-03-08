package pro.bashkatov.pokerapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pro.bashkatov.pokerapi.entity.Room;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
}
