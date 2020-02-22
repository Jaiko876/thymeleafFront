package qas.uicontroller.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest httpServletRequest) {
        return "home";
    }
}
