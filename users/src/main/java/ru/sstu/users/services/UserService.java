package ru.sstu.users.services;

import ru.sstu.users.models.enums.Role;
import ru.sstu.users.repositories.RoleRepository;
import ru.sstu.users.repositories.UserRepository;
import ru.sstu.users.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(User user) {
        if (userRepository.findByLogin(user.getLogin()) != null || userRepository.findByEmail(user.getEmail()) != null)
            return;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        List<String> listRoles = new ArrayList<>();
        listRoles.add(roleRepository.save(Role.ROLE_USER, createdUser.getLogin()));
        Set<Role> roles = new HashSet<>();
        for (String listRole : listRoles) {
            roles.add(Role.valueOf(listRole));
        }
        createdUser.setRoles(roles);
    }

    public User getUserByPrincipal(Principal principal) {
        User user = userRepository.findByLogin(principal.getName());
        user.setEmail(null);
        user.setPassword(null);
        return user;
    }

    public User getUserByLogin(String login) {
        User user = userRepository.findByLogin(login);
        user.setEmail(null);
        user.setPassword(null);
        return user;
    }

}
