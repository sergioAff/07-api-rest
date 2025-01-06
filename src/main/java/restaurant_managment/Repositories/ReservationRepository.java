package restaurant_managment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant_managment.Models.ReservationModel;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {
}