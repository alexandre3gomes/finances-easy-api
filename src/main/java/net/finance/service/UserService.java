package net.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.finance.bo.UserBo;
import net.finance.entity.User;

import lombok.NonNull;

@RestController()
@RequestMapping("/user")
public class UserService {

	@NonNull private final UserBo userBo;

	@Autowired
	public UserService(final UserBo devBo) {
		this.userBo = devBo;
	}

	@GetMapping("/current")
	public User getDeveloper(@AuthenticationPrincipal final User dev) {
		return dev;
	}

	@GetMapping("/logout")
	public boolean logout(@AuthenticationPrincipal final User dev) {
		userBo.logout(dev);
		return true;
	}

	@PutMapping("/create")
	public ResponseEntity<User> create(@RequestBody final User dev) {
		return new ResponseEntity<>(userBo.create(dev), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<User> update(@RequestBody final User dev) {
		return new ResponseEntity<>(userBo.update(dev), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		userBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<User>> list() {
		return new ResponseEntity<>(userBo.listAll(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<User> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(userBo.findById(id), HttpStatus.OK);
	}
}
