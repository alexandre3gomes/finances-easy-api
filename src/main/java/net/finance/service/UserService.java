package net.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import net.finance.bo.UserBo;
import net.finance.entity.User;

@RestController()
@RequestMapping("/user")
public class UserService {

	@NonNull
	private final UserBo userBo;

	@Autowired
	public UserService(final UserBo userBo) {
		this.userBo = userBo;
	}

	@PostMapping("/create")
	public ResponseEntity<User> create(@RequestBody final User user) {
		return new ResponseEntity<>(userBo.create(user), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		userBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<User> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(userBo.get(id), HttpStatus.OK);
	}

	@GetMapping("/current")
	public User getUser(@AuthenticationPrincipal final User user) {
		return user;
	}

	@GetMapping("/list")
	public ResponseEntity<Page<User>> list(@RequestParam(defaultValue = "0") final int page,
			@RequestParam(defaultValue = "10") final int size, @RequestParam(defaultValue = "name") final String order,
			@RequestParam(defaultValue = "DESC") final Sort.Direction direction) {
		return new ResponseEntity<>(userBo.list(PageRequest.of(page, size, new Sort(direction, order))), HttpStatus.OK);
	}

	@GetMapping("/logout")
	public boolean logout(@AuthenticationPrincipal final User user) {
		userBo.logout(user);
		return true;
	}

	@PostMapping("/update")
	public ResponseEntity<User> update(@RequestBody final User user) {
		return new ResponseEntity<>(userBo.update(user), HttpStatus.OK);
	}

}
