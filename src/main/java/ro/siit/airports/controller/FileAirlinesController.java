package ro.siit.airports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ro.siit.airports.domain.Airlines;
import ro.siit.airports.service.FileServiceAirlines;

import java.util.List;

@Controller
public class FileAirlinesController {

    @Autowired
    private FileServiceAirlines fileService;

    @RequestMapping("/file/airlines")
    public ModelAndView showFilePage() {
        final ModelAndView mav = new ModelAndView("file-page-airlines");
        return mav;
    }
    @RequestMapping(method = RequestMethod.POST, path = "/post-file-airlines")
    public String postFilePage(@ModelAttribute("url") final String url, final Model model) {
        final List<String> content = fileService.readContent(url);
        final List<Airlines> airlines = fileService.transformContent(content);
        boolean result = fileService.insertAll(airlines);
        model.addAttribute("allAirlines", result);
        return "file-page-successful-airlines";
    }

}
