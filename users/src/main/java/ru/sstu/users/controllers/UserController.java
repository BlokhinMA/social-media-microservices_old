package ru.sstu.users.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sstu.users.models.User;
import ru.sstu.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @PostMapping("/sign_up")
    public void create(User user) {
        userService.create(user);
    }

    @GetMapping("/my_profile")
    public User getUserByPrincipal(Principal principal) {
        return userService.getUserByPrincipal(principal);
    }

    @GetMapping("/profile/{login}")
    public User getUserByLogin(@PathVariable String login, Principal principal) {
        return userService.getUserByLogin(login);
    }

}
