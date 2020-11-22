package ro.siit.airports.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.siit.airports.domain.Airport;
import ro.siit.airports.domain.Flight;
import ro.siit.airports.repository.AirportRepository;
import ro.siit.airports.repository.FlightRepository;
import ro.siit.airports.service.FlightService;

import java.util.List;
import java.util.Optional;


@Controller
public class AdminController {


    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping({"/admin"})
    public String displayAdmin(final Model model) {
        final List<Flight> flights = flightRepository.findAll();
        model.addAttribute("flights", flights);
        return "admin";
    }

    @GetMapping({"/admin/edit/{flightId}"})
    public String editFlight(final Model model, @PathVariable("flightId") final Long id) {
        final List<Airport> airports = airportRepository.findByCountry("Romania");
        final Optional<Flight> optionalFlight = flightRepository.findById(id);
        Flight flight = optionalFlight.get();
        model.addAttribute("myFlight", flight);
        model.addAttribute("myAirports", airports);
        return "edit-flight";
    }

    @PostMapping("/admin")
    public String displayResult(final Model model, @ModelAttribute final Flight myFlight) {
        flightService.insertIntoDatabase(myFlight);
        final List<Flight> flights = flightRepository.findAll();
        model.addAttribute("flights", flights);
        return "admin";
    }

    @GetMapping({"/admin/add"})
    public String addFlight(final Model model) {
        final List<Airport> airports = airportRepository.findAll();
        model.addAttribute("myAirports", airports);
        model.addAttribute("flight", new Flight());
        return "add-flight";
    }


}
