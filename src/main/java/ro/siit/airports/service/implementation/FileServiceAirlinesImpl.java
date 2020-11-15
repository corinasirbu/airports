package ro.siit.airports.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.airports.domain.Airlines;
import ro.siit.airports.repository.AirlinesRepository;
import ro.siit.airports.service.FileServiceAirlines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceAirlinesImpl implements FileServiceAirlines {

    @Autowired
    private AirlinesRepository airlinesRepository;

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
    public List<Airlines> transformContent(final List<String> content) {
        return content.stream()
                .map(element -> transformElement(element))
                .collect(Collectors.toList());
    }
    private Airlines transformElement(final String element) {
        final String[] parts = element.trim().replaceAll("\"", "").replace("\\N","").split(",");
        final Airlines airlines = new Airlines();
        airlines.setName(parts[1]);
        airlines.setAlias(parts[2]);
        airlines.setIata(parts[3]);
        airlines.setIcao(parts[4]);
        airlines.setCallsign(parts[5]);
        airlines.setCountry(parts[6]);
        airlines.setActive(Boolean.valueOf(parts[7]));
        return airlines;
    }
    public boolean insertAll(final List<Airlines> airlines) {
        return airlinesRepository.saveAll(airlines).stream()
                .map(a -> a.getId() > 0)
                .reduce(true, (p, q) -> p && q);
    }

}
