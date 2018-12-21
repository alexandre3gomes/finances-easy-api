package net.finances.easy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/home")
	public String home() {
		return "Hello";
	}

	@RequestMapping("/")
	public String index() {
		return "Nooooooossa";
	}
}
