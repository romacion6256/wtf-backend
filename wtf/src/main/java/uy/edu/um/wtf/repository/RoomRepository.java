package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByBranchAndNumber(String branch, int number);
}
