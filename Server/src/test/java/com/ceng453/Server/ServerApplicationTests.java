package com.ceng453.Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private User user1, user2, user3, user4, user5, user6, user7, user;
    private Score score1;
    private Score score2;
    private Score score3;
    private Score score4;

    @Before
	public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	    LocalDateTime time = LocalDateTime.now();
	    user  = new User("username", "email@gmail.com", "password");
	    user1 = new User("username1", "email1@dat.com", "password");
        user2 = new User("username2", "email2@dat.com", "password");
        user3 = new User("username3", "email3@dat.com", "password");
        user4 = new User("username4", "email4@dat.com", "password");
        user5 = new User("username5", "email5@dat.com", "password");
        user6 = new User("username6", "email6@dat.com", "password");
        user7 = new User("username7", "email7@dat.com", "password");

        score1 = new Score(user5, time, 12);
        score2 = new Score(user6, time, 143);
        score3 = new Score(user6, time, 32);
        score4 = new Score(user6, time, 23);
	}

    @Test public void signUp() throws Exception {
        this.mockMvc.perform(post("/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
        // We assert that the User is added to the database
        // So !isEmpty() will return True
        assertFalse(userRepository.findBySession(user.getSession()).isEmpty());
    }

    @Test
    public void deleteUser() throws Exception {
        // We save the user so that we can delete
        userRepository.save(user2);

        this.mockMvc.perform(delete("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2)));

        assertTrue(userRepository.findBySession(user2.getSession()).isEmpty());
    }

    @Test
    public void updateUser() throws Exception {
        JSONObject request = new JSONObject();
        request.put("username", "CHANGED");
        request.put("password", "password");
        request.put("email", user3.getEmail());
        request.put("token", user3.getSession());

        userRepository.save(user3);

        this.mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        User updatedUser = userRepository.findBySession(user3.getSession()).get(0);
        assertEquals("CHANGED", updatedUser.getUsername());
    }

    @Test
    public void login() throws Exception {
        JSONObject request = new JSONObject();
        request.put("username", user4.getUsername());
        request.put("password", "password");

        userRepository.save(user4);

        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        String[] tokenList = result.getResponse().getContentAsString().split("\"");

        // user4.getSession() will be equal to the old Session
        // After login we ensures that user4 will have a new session
        // We did not reflect to the user4 in here intentionally
        // in order to show that it has changed

        assertNotEquals(user4.getSession(), tokenList[3]);
        userRepository.deleteBySession(tokenList[3]);
    }

    @Test
    public void wrongJSONLogin() throws Exception {
        // In this test we attempt to login with wrong json
        JSONObject request = new JSONObject();

        // We change the key username
        request.put("usernameeeeeeeeeeeee", user7.getUsername());
        request.put("password", "password");

        userRepository.save(user7);

        // We Expect that this kind of behaviour is forbidden
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());

        userRepository.delete(user7);
    }

    @Test
    public void addNewScore() throws Exception {
        // We first add the user so that we can add scores to some User
        userRepository.save(user5);
        // To get a valid user_id and token we have to add user to the database
        JSONObject request = new JSONObject();
        request.put("token", score1.getUser().getSession());
        request.put("score", score1.getScore());

        this.mockMvc.perform(post("/score")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

        List<Score> score = scoreRepository.findByUser(user5);
        // We assert that sessionId of the saved User is same with the
        // sessionId of the score whose user is User5
        assertEquals(user5.getSession(), score.get(0).getUser().getSession());
    }

    @Test
    public void deleteScore() throws Exception {
        // We add the User to be deleted to the database First
        userRepository.save(user6);

        // We also add Scores so that we can show that the scores of the deleted User
        // will also be deleted
        scoreRepository.save(score2);
        scoreRepository.save(score3);
        scoreRepository.save(score4);

        this.mockMvc.perform(delete("/score")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(score2)));

        // We assert that there is no such score
        // Because there is no such user
        List<Score> scoreList = scoreRepository.findByUser(user6);
        assertEquals(2, scoreList.size());
    }

    @After
    public void after() {
        // What happened to the good old CCleaner ?
        userRepository.deleteBySession(user.getSession());
        userRepository.deleteBySession(user2.getSession());
        userRepository.deleteBySession(user3.getSession());
        userRepository.deleteBySession(user5.getSession());
        userRepository.deleteBySession(user6.getSession());
    }



}
