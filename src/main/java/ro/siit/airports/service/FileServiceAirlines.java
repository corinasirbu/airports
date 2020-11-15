package ro.siit.airports.service;

import ro.siit.airports.domain.Airlines;


import java.util.List;

public interface FileServiceAirlines {

    List<String> readContent(String fileName);

    List<Airlines> transformContent(List<String> content);

    boolean insertAll(List<Airlines> airlines);

}
