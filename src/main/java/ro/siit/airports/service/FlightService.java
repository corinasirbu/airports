package ro.siit.airports.service;

import ro.siit.airports.domain.Flight;

import java.util.List;


public interface FlightService {

    Flight insertIntoDatabase(Flight message);

}
