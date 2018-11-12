package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScoreController {

    // This interface is implemented by the Spring itself.
    // By using this interface we will make CRUD operations

    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping(path="/allScores")
    public Iterable<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    @GetMapping(path="/userScores")
    public List<Score> getUserScores(@RequestBody User user) {
        return scoreRepository.findByUser(user);
    }

    @PostMapping(path="/score")
    public Score addNewScore(@RequestBody Score score) {
        // TODO Do we need to update the user's Score list ?
       return scoreRepository.save(score);
    }

    @DeleteMapping(path="/score")
    public void deleteScore(@RequestBody Score score ) {
        // TODO Anyone can delete a score without being a user
        scoreRepository.deleteById(score.getScore_id());
    }
}
