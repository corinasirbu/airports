package ro.siit.airports.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResetController {

    @GetMapping({"/reset-password"})
    public String displayResetPage() {
        return "reset-password";
    }
}
