package domain.bell.repository;

import domain.bell.entity.Bell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BellRepository extends JpaRepository<Bell, Long> {
}
