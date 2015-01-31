package io.perculate.pot;

import io.perculate.readings.ReadingRepository;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/pot")
public class PotController {
	@Inject
	private ReadingRepository readingRepository;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("readings", readingRepository.findAll());
		return "pot/index";
	}
	
}
