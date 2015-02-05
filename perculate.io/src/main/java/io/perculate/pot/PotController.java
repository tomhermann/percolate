package io.perculate.pot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pot")
public class PotController {

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "pot/index";
	}

    @RequestMapping(value = "/readings", method = RequestMethod.GET)
    public String readings() {
        return "pot/readings";
    }
}
