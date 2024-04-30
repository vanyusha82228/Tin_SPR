package edu.java.scrapper.domain.jpaRepository;

import edu.java.scrapper.domain.modelJpa.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaResourceRepository extends JpaRepository<Resource, Long> {
}
