package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class UserController {

    // This interface is implemented by the Spring itself.
    // By using this interface we will make CRUD operations

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/signup")
    public User addNewUser (@RequestBody User user) throws NoSuchAlgorithmException {
        return userRepository.save(user);
    }

    @DeleteMapping(path="/user")
    public void deleteUser(@RequestBody User user) {
       userRepository.deleteBySession(user.getSession());
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

    /*
    @GetMapping(path="/start_game")
    public String startNewGame(@RequestParam String token) {
        if( userRepository.findBySession(token).size() > 0 )
            gameService.CreateNewInstance( token );
        return "Good good";
    }
    */


}
