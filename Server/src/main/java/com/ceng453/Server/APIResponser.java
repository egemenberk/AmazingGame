package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
public class APIResponser { // delete, login, get_board, use mappings other than get, test :(, documentation

    // We create two repository(Interface type) for two tables in our database
    // These two interfaces are implemented by the Spring itself.
    // By using these interfaces we will make CRUD operations
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
        return userRepository.save(user);
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

    @GetMapping(path="/userScores")
    public List<Score> getUserScores(@RequestBody User user) {
        //return user.getScoreLogs(); // It returns empty list does not fetch the children
        return scoreRepository.findByUser(user);
    }

    @PostMapping(path="/score")
    public Score addNewScore(@RequestBody Score score) {
        return scoreRepository.save(score);
    }

    @DeleteMapping(path="/score")
    public void deleteScore(@RequestBody User user, @RequestParam Integer score_id) {
        // TODO
        //scoreRepository.deleteById(score.getScore_id());
    }


}
