package net.finance.service;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.finance.bo.UserBo;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/public/logon")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class LogonService {

	@NonNull UserBo devBo;

	@SuppressWarnings("unused")
	@PostMapping("/login")
	String login(final HttpServletRequest request, @RequestParam("username") final String username, @RequestParam("password") final String password) {
		return devBo.login(username, password).orElseThrow(() -> new RuntimeException("invalid login and/or password"));
	}

}
