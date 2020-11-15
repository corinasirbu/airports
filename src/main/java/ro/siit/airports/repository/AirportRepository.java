package ro.siit.airports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.siit.airports.domain.Airport;
import ro.siit.airports.domain.Flight;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    List<Airport> findByCountryAndCity(String country, String city);

    List<Airport> findByCountry(String country);

    List<Airport> findByCity(String city);

    List<Airport> findById(long id);

}
