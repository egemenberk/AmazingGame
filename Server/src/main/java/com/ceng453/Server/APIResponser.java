package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


@RestController
public class APIResponser { // delete, login, get_board, use mappings other than get, test :(, documentation

    @Autowired
    private UserRepository userRepository;

    private GameService gameService;

    @PostConstruct
    public void init() {
        gameService = new GameService();
    }

    @PostMapping(path="/signup")
    public User addNewUser (@RequestBody User user) throws NoSuchAlgorithmException {
        userRepository.save(user);
        return user;
    }

    @DeleteMapping(path="/user")
    public void deleteUser(@RequestBody User user) {
       userRepository.deleteBySession(user.getSession());
    }

    @GetMapping(path="/start_game")
    public String startNewGame(@RequestParam String token) {
        if( userRepository.findBySession(token).size() > 0 )
            gameService.CreateNewInstance( token );
        return "Good good";
    }

    @GetMapping(path="/all")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path="/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) throws NoSuchAlgorithmException {
        return userRepository.authenticate( username, password );
    }
}
