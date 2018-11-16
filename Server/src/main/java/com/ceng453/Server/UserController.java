package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
    public Object deleteUser(@Valid @RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<User> userList = userRepository.findBySession(user.getSession());
        if(userList.isEmpty())
            return new ResponseEntity<String>("There is no such user, check Your token", headers, HttpStatus.BAD_REQUEST);

        userRepository.deleteBySession(user.getSession());
        return new ResponseEntity<String>("User with username: " + user.getUsername() + "is succesfully deleted", headers, HttpStatus.OK);
    }

    @GetMapping(path="/allUsers") // ONLY FOR DEBUG PURPOSES
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @PostMapping(path="/login")
    public Object login( @RequestBody Map<String, String> payload) throws NoSuchAlgorithmException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if(payload.get("username") != null && payload.get("password") != null)
            return userRepository.authenticate( payload.get("username"), payload.get("password") );
        else
            return new ResponseEntity<String>("You did not provide username or password" +
                    " field, Please try again later", headers, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/user")
    public Object updateUser(@RequestBody Map<String, String> payload) throws NoSuchAlgorithmException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        List<User> userList = userRepository.findBySession(payload.get("token"));

        if(userList.size() == 0)
            return new ResponseEntity<String>("Your token is wrong, Please try again later", headers, HttpStatus.BAD_REQUEST);

        User user = userList.get(0);

        if(payload.get("password") != null)
            user.setPassword( payload.get("password") );
        else
            return new ResponseEntity<String>("You did not provide password field", headers, HttpStatus.BAD_REQUEST);

        if(payload.get("username") != null)
            user.setUsername( payload.get("username") );
        else
            return new ResponseEntity<>("You did not provide username field", HttpStatus.BAD_REQUEST);
        if(payload.get("email") != null)
            user.setEmail( payload.get("email") );
        else
            return new ResponseEntity<>("You did not provide email field", HttpStatus.BAD_REQUEST);

        return userRepository.save(user);
    }

}
