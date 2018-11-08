package com.ceng453.Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;


@RestController
public class APIResponser { // delete, login, get_board, use mappings other than get, test :(, documentation
    @Autowired
    private UserRepository userRepository;

    private GameService gameService;

    private Gson gson;

    @PostConstruct
    public void init() {
        gameService = new GameService();
        gson = new GsonBuilder().create();
    }

    @GetMapping(path="/add")
    public String addNewUser (@RequestParam String username, @RequestParam String email, @RequestParam String password) {

        User n = new User();
        n.setUsername(username);
        n.setEmail(email);
        n.setPassword_encrypted(password);
        userRepository.save(n);
        return gson.toJson("Success");
    }

    @GetMapping(path="/start_game")
    public String startNewGame(@RequestParam String token) {
        if( userRepository.findByToken(token).size() > 0 )
            gameService.CreateNewInstance( token );
        return gson.toJson("Success");
    }

    @GetMapping(path="/all")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path="/login")
    public String login(@RequestParam String username, @RequestParam String password) throws NoSuchAlgorithmException {
        return gson.toJson( userRepository.authenticate( username, password ) );
    }
}
