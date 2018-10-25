package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIResponser {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/add")
    public String addNewUser (@RequestParam String username, @RequestParam String email, @RequestParam String password) {

        User n = new User();
        n.setUsername(username);
        n.setEmail(email);
        n.setPassword_encrypted(password);
        userRepository.save(n);
        return "Success";
    }

    @GetMapping(path="/all")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
