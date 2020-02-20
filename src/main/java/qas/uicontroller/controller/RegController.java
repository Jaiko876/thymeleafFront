package qas.uicontroller.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.RegForm;


@Controller
@RequestMapping("/")
public class RegController {
    @RequestMapping(value = "reg", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("regForm", new RegForm());
        return "reg";
    }

    @RequestMapping(value = "reg", method = RequestMethod.POST)
    public String register(@ModelAttribute("regForm") RegForm regForm) {
        System.out.println(regForm.toString());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<RegForm> request = new HttpEntity<>(regForm);
        restTemplate.exchange("http://localhost:8084/register", HttpMethod.POST, request,
                ResponseEntity.class);
        System.out.println("jopa");
        return "redirect:/login";
    }

}
