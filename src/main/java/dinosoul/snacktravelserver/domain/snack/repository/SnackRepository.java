package dinosoul.snacktravelserver.domain.snack.repository;

import dinosoul.snacktravelserver.domain.snack.entity.Snack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long> {
}
