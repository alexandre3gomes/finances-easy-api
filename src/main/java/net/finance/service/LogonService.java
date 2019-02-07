package net.finance.service;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.finance.bo.UserBo;
import net.finance.entity.User;

@RestController
@RequestMapping("/public/logon")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class LogonService {

	@NonNull
	UserBo devBo;

	@SuppressWarnings("unused")
	@PostMapping("/login")
	String login(final HttpServletRequest request, @RequestBody final User user) {
		return devBo.login(user.getUsername(), user.getPassword())
				.orElseThrow(() -> new RuntimeException("invalid login and/or password"));
	}

	@GetMapping("/test")
	String test() {
		return "Works";
	}

}
