package ro.siit.airports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.siit.airports.domain.Airport;
import ro.siit.airports.domain.Flight;
import ro.siit.airports.model.Search;
import ro.siit.airports.repository.AirportRepository;
import ro.siit.airports.repository.FlightRepository;
import ro.siit.airports.service.AirportService;
import ro.siit.airports.service.FlightService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class AirportController {

    @Autowired
    private AirportService airportService;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @GetMapping("/search")
    public ModelAndView displaySearchPage() {
        final ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("search", new Search());
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView displaySearchResults(final Search search) {
        final ModelAndView modelAndView = new ModelAndView("search-result");
        final List<Airport> list = airportService.findFilteredAirports(search);
        modelAndView.addObject("myAirports", list);
        return modelAndView;
    }

    @GetMapping("/airport/{airportLink}")
    public ModelAndView displayAirportByName(@PathVariable("airportLink") final long id) {
        final ModelAndView mav = new ModelAndView("airport-description");

        //bring Airport name, city, country
        final Optional<Airport> airports = airportRepository.findById(id);
        final String airport = airports.stream()
                .findFirst()
                .map(f -> f.getName() + " -> " + f.getCity() + ", " + f.getCountry()
                        )
                .orElse("no data");
        mav.addObject("airport", airport);

        //details about each airport
        final Optional<Airport> airportDetails = airportRepository.findById(id);
        final String details = airportDetails.stream()
                .findFirst()
                .map(f -> f.getDetails()
                )
                .orElse("no data");
        mav.addObject("details", details);

        //maps for each airport
        final Optional<Airport> airportMaps = airportRepository.findById(id);
        final String maps = airportMaps.stream()
                .findFirst()
                .map(f -> f.getLocation()
                )
                .orElse("no data");
        mav.addObject("maps", maps);

        //departure flights
        final Optional<Airport> departureAirport = airportRepository.findById(id);
        final Airport airportDeparture = departureAirport.get();
        final List<Flight> departureFlights = departureAirport.map(listDf -> flightRepository.findByDepartureAirport(airportDeparture).orElse(new ArrayList<>())).get();
        mav.addObject("departureFlights", departureFlights);

        //departure flights
        final Optional<Airport> arrivalAirport = airportRepository.findById(id);
        final Airport airportArrival = arrivalAirport.get();
        final List<Flight> arrivalFlights = arrivalAirport.map(listDf -> flightRepository.findByArrivalAirport(airportDeparture).orElse(new ArrayList<>())).get();
        mav.addObject("arrivalFlights", arrivalFlights);

        return mav;
    }
}

//functional flight view with both Departure and Arrival airport
        /*
        final Optional<Airport> airportSearch = airportRepository.findById(id);
        final String airportSearchName = airportSearch.map(a -> a.getName() + a.getCity()).orElse("Fara nume");
        final List<Flight> flights = airportSearch.map(a -> flightRepository.findByDepartureAirportOrArrivalAirport(a, a)).orElse(new ArrayList<>());
        final Airport newAirport = airportSearch.get();
        mav.addObject("flights", flights);
        */

