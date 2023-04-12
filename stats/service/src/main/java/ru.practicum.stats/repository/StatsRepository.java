package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.model.EndpointHit;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
}
