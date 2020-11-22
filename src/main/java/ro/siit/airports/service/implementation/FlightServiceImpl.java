package ro.siit.airports.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.airports.domain.Flight;
import ro.siit.airports.repository.FlightRepository;
import ro.siit.airports.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;


    @Override
    public Flight insertIntoDatabase(final Flight flight) {
        return flightRepository.save(flight);
    }
}
