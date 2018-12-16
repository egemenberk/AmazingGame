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

import java.util.List;
import java.util.Map;

@RestController
public class ScoreController {

    // This interface is implemented by the Spring itself.
    // By using this interface we will make CRUD operations

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserRepository userRepository;

    /*
     * A method that adds score log to the db
     * Will be called by frontend for reporting gained scores
     */
    @PostMapping(path="/score")
    public Object addNewScore(@RequestBody Map<String, String> payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Score s = new Score();

        if(payload.get("score") != null)
            s.setScore( Integer.parseInt(payload.get("score")) );
        else
            return new ResponseEntity<>("Your score entity cannot be found", headers, HttpStatus.BAD_REQUEST);

        if(payload.get("token") != null){
            List<User> userList = userRepository.findBySession( payload.get("token") );
            if(userList.size() == 0)
                return new ResponseEntity<>("Your token is incorrect, Please try again later", headers, HttpStatus.BAD_REQUEST);

            s.setUser(userList.get(0));
        }
        else
            return new ResponseEntity<>("Your token entity cannot be found", headers, HttpStatus.BAD_REQUEST);

        return scoreRepository.save(s);
    }

    /*
     * An endpoint for getting the leaderboard data for all time
     */
    @GetMapping(path="/leaderboard/all_time")
    public List<Map<String,String>> getAllTimeLeaders(){
        return userRepository.getLeaderboardforAllTime();
    }

    /*
     * An endpoint for getting the leaderboard data for past 7 days
     */
    @GetMapping(path="/leaderboard/7_days") // This can easily extendible to N days
    public List<Map<String,String>> get7DaysLeaderboard(){
        return userRepository.getLeaderboardfor7days();
    }

    @DeleteMapping(path="/score") // ONLY FOR DEBUG PURPOSES User cannot delete its score
    public Object deleteScore(@RequestBody Score score ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        List<Score> scoresList = scoreRepository.findByUser(score.getUser());
        if(scoresList.isEmpty())
            return new ResponseEntity<>("There is no such score", headers, HttpStatus.BAD_REQUEST);


        scoreRepository.deleteById(score.getScore_id());
        return new ResponseEntity<>("Score with Id: " + score.getScore_id() + "is succesfully deleted", headers, HttpStatus.OK);
    }

    @GetMapping(path="/allScores") // ONLY FOR DEBUG PURPOSES
    public Iterable<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    /*
     * An endpoint that returns all hşstory of gathered points of user
     * Will probably used for debug purposes
     */
    @GetMapping(path="/userScores")
    public List<Score> getUserScores(@RequestBody User user) {
        return scoreRepository.findByUser(user);
    }
}
