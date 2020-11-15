package ro.siit.airports.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.airports.domain.Airport;
import ro.siit.airports.repository.AirportRepository;
import ro.siit.airports.service.FileServiceAirport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceAirportImpl implements FileServiceAirport {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<String> readContent(final String fileName) {
        final List<String> result = new ArrayList<>();
        try (InputStream is = (new URL(fileName)).openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Airport> transformContent(final List<String> content) {
        return content.stream()
                .map(element -> transformElement(element))
                .collect(Collectors.toList());
    }
    private Airport transformElement(final String element) {
        final String[] parts = element.trim().replaceAll("\"", "").replace("\\N",null).split(",");
        final Airport airport = new Airport();
        airport.setName(parts[1]);
        airport.setCity(parts[2]);
        airport.setCountry(parts[3]);
        airport.setIata(parts[4]);
        airport.setIcao(parts[5]);
        airport.setLatitude(new BigDecimal(parts[6]));
        airport.setLongitude(new BigDecimal(parts[7]));
        airport.setAltitude(Integer.valueOf(parts[8]));
        airport.setTimezone(Double.valueOf(parts[9]));
        airport.setDst(parts[10].charAt(0));
        airport.setTz(parts[11]);
        airport.setType(parts[12]);
        airport.setSource(parts[13]);
        return airport;
    }
    public boolean insertAll(final List<Airport> airports) {
        return airportRepository.saveAll(airports).stream()
                .map(a -> a.getId() > 0)
                .reduce(true, (p, q) -> p && q);
    }

}
