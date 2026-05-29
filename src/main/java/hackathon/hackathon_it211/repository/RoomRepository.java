package hackathon.hackathon_it211.repository;

import hackathon.hackathon_it211.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomNumber(Integer roomNumber);

    List<Room> findByRoomTypeContainingIgnoreCase(String roomType);

    @Query("SELECT r FROM Room r WHERE r.isDeleted = false AND " +
           "(:roomNumber IS NULL OR r.roomNumber = :roomNumber) AND " +
           "(:roomType IS NULL OR LOWER(r.roomType) LIKE LOWER(CONCAT('%', :roomType, '%')))")
    List<Room> searchRooms(@Param("roomNumber") Integer roomNumber, @Param("roomType") String roomType);

    @Query("SELECT r FROM Room r WHERE r.isDeleted = false")
    List<Room> findAllActiveRooms();
}
