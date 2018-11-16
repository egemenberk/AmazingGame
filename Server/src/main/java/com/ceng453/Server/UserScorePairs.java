package com.ceng453.Server;


/*
 * A class for converting Leaderboard raw data to an object which will be serialized to json
 */
public class UserScorePairs{

    String user_id,username,score;

    public UserScorePairs(String user_id, String username, String score){
        this.user_id = user_id;
        this.username = username;
        this.score = score;
    }
}
