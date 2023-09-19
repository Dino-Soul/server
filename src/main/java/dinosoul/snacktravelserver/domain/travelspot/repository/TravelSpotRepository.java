package dinosoul.snacktravelserver.domain.travelspot.repository;

import dinosoul.snacktravelserver.domain.travelspot.entity.TravelSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelSpotRepository extends JpaRepository<TravelSpot, Long> {
}
