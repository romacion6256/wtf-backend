package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
