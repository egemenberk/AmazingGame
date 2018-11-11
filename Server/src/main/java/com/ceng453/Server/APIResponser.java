package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;


@RestController
public class APIResponser { // delete, login, get_board, use mappings other than get, test :(, documentation

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

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

    @GetMapping(path="/allUsers")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path="/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) throws NoSuchAlgorithmException {
        return userRepository.authenticate( username, password );
    }

    @GetMapping(path="/allScores")
    public Iterable<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    @PostMapping(path="/score")
    public Score addNewScore(@RequestBody Score score) {
        return scoreRepository.save(score);
    }


}
