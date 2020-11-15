package ro.siit.airports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ro.siit.airports.domain.Airport;
import ro.siit.airports.domain.Flight;
import ro.siit.airports.model.Search;
import ro.siit.airports.repository.AirportRepository;
import ro.siit.airports.repository.FlightRepository;
import ro.siit.airports.service.AirportService;

import java.util.List;


@Controller
public class AirportController {

    @Autowired
    private AirportService airportService;

    @Autowired
    private AirportRepository airportRepository;

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
        final List<Airport> airports = airportRepository.findById(id);
        final String airport = airports.stream()
                .findFirst()
                .map(f -> f.getName() + " -> " + f.getCity() + ", " + f.getCountry()
                        )
                .orElse("no data");
        mav.addObject("airport", airport);
        final List<Airport> airportDetails = airportRepository.findById(id);
        final String details = airportDetails.stream()
                .findFirst()
                .map(f -> f.getDetails()
                )
                .orElse("no data");
        mav.addObject("details", details);
        final List<Airport> airportMaps = airportRepository.findById(id);
        final String maps = airportMaps.stream()
                .findFirst()
                .map(f -> f.getLocation()
                )
                .orElse("no data");
        mav.addObject("maps", maps);
        return mav;
    }



}
