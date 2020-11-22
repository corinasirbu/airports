package ro.siit.airports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.siit.airports.domain.Airport;
import ro.siit.airports.repository.AirportRepository;


import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping({"/", "/home", "/index"})
    public String displayHomePage(final Model model) {
        final List<Airport> airports = airportRepository.findAll();
        model.addAttribute("myAirports", airports);
        return "home-page";
    }
}
