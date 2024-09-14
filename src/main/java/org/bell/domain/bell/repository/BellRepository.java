package org.bell.domain.bell.repository;

import org.bell.domain.bell.entity.Bell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BellRepository extends JpaRepository<Bell, Long> {
}
