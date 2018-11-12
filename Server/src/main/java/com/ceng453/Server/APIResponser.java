package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
public class APIResponser { // delete, login, get_board, use mappings other than get, test :(, documentation

    // We create two repository(Interface type) for two tables in our database

    private GameService gameService;

    @PostConstruct
    public void init() {
        gameService = new GameService();
    }

}
