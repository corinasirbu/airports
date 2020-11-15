package ro.siit.airports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ro.siit.airports.domain.Airport;
import ro.siit.airports.service.FileServiceAirport;

import java.util.List;

@Controller
public class FileAirportController {

    @Autowired
    private FileServiceAirport fileService;

    @RequestMapping("/file")
    public ModelAndView showFilePage() {
        final ModelAndView mav = new ModelAndView("file-page");
        return mav;
    }
    @RequestMapping(method = RequestMethod.POST, path = "/post-file")
    public String postFilePage(@ModelAttribute("url") final String url, final Model model) {
        final List<String> content = fileService.readContent(url);
        final List<Airport> airports = fileService.transformContent(content);
        boolean result = fileService.insertAll(airports);
        model.addAttribute("allGucci", result);
        return "file-page-successful";
    }

}
