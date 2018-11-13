package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class UserController {

    // This interface is implemented by the Spring itself.
    // By using this interface we will make CRUD operations

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    @PostMapping(path="/signup")
    public User addNewUser (@Valid @RequestBody User user) {
        User u = userRepository.save(user);
        // Adding an empty score entity for that user to be able to list him/her on leaderboards when score=0 too
        Score s = new Score();
        s.setUser(u);
        s.setScore(0);
        scoreRepository.save(s);
        return u;
    }

    @DeleteMapping(path="/user")
    public void deleteUser(@Valid @RequestBody User user) {
       userRepository.deleteBySession(user.getSession());
    }

    @GetMapping(path="/allUsers")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @PostMapping(path="/login")
    public Map<String, String> login( @RequestBody Map<String, String> payload) throws NoSuchAlgorithmException {
        return userRepository.authenticate( payload.get("username"), payload.get("password") );
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody Map<String, String> payload) throws NoSuchAlgorithmException {

        User user = userRepository.findBySession(payload.get("token")).get(0);
        user.setPassword( payload.get("password") );
        user.setUsername( payload.get("username") );
        user.setEmail( payload.get("email") );

        return userRepository.save(user);
    }

}
