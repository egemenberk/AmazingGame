package com.ceng453.Server;

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

    @GetMapping(path="/userScores")
    public List<Score> getUserScores(@RequestBody User user) {
        return scoreRepository.findByUser(user);
    }

    @PostMapping(path="/score")
    public Object addNewScore(@RequestBody Map<String, String> payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Score s = new Score();

        if(payload.get("score") != null)
            s.setScore( Integer.parseInt(payload.get("score")) );
        else
            return new ResponseEntity<String>("Your score entity cannot be found", headers, HttpStatus.BAD_REQUEST);

        if(payload.get("token") != null){
            List<User> userList = userRepository.findBySession( payload.get("token") );
            if(userList.size() == 0)
                return new ResponseEntity<String>("Your token is incorrect, Please try again later", headers, HttpStatus.BAD_REQUEST);

            s.setUser(userList.get(0));
        }
        else
            return new ResponseEntity<String>("Your token entity cannot be found", headers, HttpStatus.BAD_REQUEST);

        return scoreRepository.save(s);
    }

    @GetMapping(path="/leaderboard/all_time")
    public List<Map<String,String>> getAllTimeLeaders(){
        return userRepository.getLeaderboardforAllTime();
    }

    @GetMapping(path="/leaderboard/7_days") // This can easily extendible to N days
    public List<Map<String,String>> get7DaysLeaderboard(){
        return userRepository.getLeaderboardfor7days();
    }

    @DeleteMapping(path="/score") // ONLY FOR DEBUG PURPOSES
    public void deleteScore(@RequestBody Score score ) {
        scoreRepository.deleteById(score.getScore_id());
    }

    @GetMapping(path="/allScores") // ONLY FOR DEBUG PURPOSES
    public Iterable<Score> getAllScores() {
        return scoreRepository.findAll();
    }
}
