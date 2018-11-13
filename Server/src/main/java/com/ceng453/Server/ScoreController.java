package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public Score addNewScore(@RequestBody Map<String, String> payload) {
        Score s = new Score();
        s.setScore( Integer.parseInt(payload.get("score")) );
        s.setUser( userRepository.findBySession( payload.get("token") ).get(0) );
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
