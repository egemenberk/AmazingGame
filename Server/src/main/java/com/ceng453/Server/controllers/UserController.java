package com.ceng453.Server.controllers;

import com.ceng453.Server.repositories.ScoreRepository;
import com.ceng453.Server.repositories.UserRepository;
import com.ceng453.Server.entities.Score;
import com.ceng453.Server.entities.User;
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

    /*
     * A method for saving a new user to the db,
     * Will be called by frontend when creating a new user
     */
    @PostMapping(path="/signup")
    public Object addNewUser (@Valid @RequestBody User user) {
        User u;
        try {
            u = userRepository.save(user);
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=utf-8");
            System.out.println("RAMRAS");
            return new ResponseEntity<String>("{\n \"Reason\": \"Same Username or email has been used\" \n}", headers, HttpStatus.CONFLICT);
        }
            // Adding an empty score entity for that user to be able to list him/her on leaderboards when score=0 too
        Score s = new Score();
        s.setUser(u);
        s.setScore(0);
        scoreRepository.save(s);
        return u;
    }

    /*
     * A method for deleting an existing user from the db,
     * Will be called by frontend when deleting an existing user
     * Authentication will be done using session_token
     */
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

    /*
     * A method for user to login. Login operation will generate a session_token
     * for the user, which will be used as identifier for that user on every other operations
     * until the user is logged again
     */
    @PostMapping(path="/login")
    public Object login( @RequestBody Map<String, String> payload) throws NoSuchAlgorithmException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if(payload.get("username") != "" && payload.get("password") != "" && payload.get("email") != "")
            return userRepository.authenticate( payload.get("username"), payload.get("password") );
        else
            return new ResponseEntity<String>("{\n \"Reason\": \"Fill the fields\" \n}", headers, HttpStatus.BAD_REQUEST);
    }

    /*
     * A method for updating an existing user on the db,
     * Will be called by frontend when updating an existing user
     * Authentication will be done using session_token
     */
    @PutMapping("/user")
    public Object updateUser(@RequestBody Map<String, String> payload) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        List<User> userList = userRepository.findBySession(payload.get("token"));

        if(userList.size() == 0)
            return new ResponseEntity<String>("Your token is wrong, Please try again later", headers, HttpStatus.BAD_REQUEST);

        User user = userList.get(0);

        // Dont raise an error, user might want not to change every field of its
        if(payload.get("password") != null)
            user.setPassword( payload.get("password") );
        if(payload.get("username") != null)
            user.setUsername( payload.get("username") );
        if(payload.get("email") != null)
            user.setEmail( payload.get("email") );

        return userRepository.save(user);
    }

    // ONLY FOR DEBUG PURPOSES
    @GetMapping(path="/allUsers")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

}
