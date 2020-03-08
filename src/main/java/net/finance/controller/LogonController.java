package net.finance.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.finance.bo.UserBo;
import net.finance.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/logon")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class LogonController {

    @NonNull
    UserBo userBo;

    @GetMapping("/test")
    static String test() {
        return "Works";
    }

    @PostMapping("/login")
    ResponseEntity<User> login(HttpServletRequest request, @RequestBody User user) {
        return new ResponseEntity<>(userBo.login(user.getUsername(), user.getPassword()).orElseThrow(NoSuchElementException::new), HttpStatus.OK);
    }

}
