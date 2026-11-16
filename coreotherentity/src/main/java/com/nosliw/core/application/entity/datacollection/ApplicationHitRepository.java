package com.nosliw.core.application.entity.datacollection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationHitRepository extends JpaRepository<ApplicationHit, Long> {
}
