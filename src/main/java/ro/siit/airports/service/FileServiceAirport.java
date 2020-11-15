package ro.siit.airports.service;

import ro.siit.airports.domain.Airport;

import java.util.List;

public interface FileServiceAirport {

    List<String> readContent(String fileName);

    List<Airport> transformContent(List<String> content);

    boolean insertAll(List<Airport> airports);

}
