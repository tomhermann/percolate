package io.perculate.pot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@RequestMapping("/pot")
public class PotController {

    private final PotModel potModel;

    @Inject
    public PotController(PotModel potModel) {
        this.potModel = potModel;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "pot/index";
    }

    @RequestMapping(value = "/graph")
    public String graph() {
        return "pot/graph";
    }

    @RequestMapping(value="/brewed", method = RequestMethod.POST)
    public @ResponseBody String potBrewed(@RequestBody BrewTime brewTime) {
        potModel.setBrewTime(brewTime);
        return "It worked!";
    }
}
