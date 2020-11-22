package ro.siit.airports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.siit.airports.domain.Airlines;


@Repository
public interface AirlinesRepository extends JpaRepository<Airlines, Long> {

}
