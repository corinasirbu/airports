package ro.siit.airports.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.airports.domain.Airport;
import ro.siit.airports.domain.Flight;
import ro.siit.airports.model.Search;
import ro.siit.airports.repository.AirportRepository;
import ro.siit.airports.service.AirportService;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {
    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<Airport> findFilteredAirports(final Search search) {
        List<Airport> list;
            if (search.hasCountry() && search.hasCity()) {
                list = airportRepository.findByCountryAndCity(search.getCountry().toLowerCase(), search.getCity().toLowerCase());
            } else if (search.hasCountry()) {
                list = airportRepository.findByCountry(search.getCountry().toLowerCase());
            } else if (search.hasCity()) {
                list = airportRepository.findByCity(search.getCity().toLowerCase());
        } else {
            list = airportRepository.findAll();
        }
        return list;
    }

}
