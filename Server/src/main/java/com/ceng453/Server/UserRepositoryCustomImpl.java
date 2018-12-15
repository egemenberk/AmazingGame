package com.ceng453.Server;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@Transactional
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final String[] key_array = { "user_id", "username", "score" };

    @PersistenceContext
    private EntityManager entityManager;

    /*
     * An authenticate method to be used only in login. If username-password pair is found out
     * to be correct, user is given an token that will be used in other API operations for
     * authenticating
     */
    @Override
    public ResponseEntity<String> authenticate(String username, String password) throws NoSuchAlgorithmException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        Query query = entityManager
                .createNativeQuery("select * from user where username=?", User.class);
        query.setParameter(1, username);

        User result;
        try {
            result = (User) query.getSingleResult();
        } catch(  NoResultException ex){
            return new ResponseEntity<> ("{\n \"Reason\": \"No user found\" \n}", headers, HttpStatus.FORBIDDEN);
        }

        if( result.getPassword().equals( EncryptionHelper.encrypt(password) ) ) {
            String generated_token = EncryptionHelper.generateToken();
            User real_user = entityManager.getReference(User.class, result.getId());
            real_user.setSession(generated_token);
            entityManager.merge(real_user);
            return new ResponseEntity<> (String.format("{\n \"Token\": \"%s\" \n}",real_user.getSession()), headers, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<> ("{\n \"Reason\": \"Auth Failed\" \n}", headers, HttpStatus.FORBIDDEN);
        }
    }


    /*
     * A query for getting all time leaderboard
     */
    @Override
    public List<Map<String, String>> getLeaderboardforAllTime() {
        return generateResponse( "select U.id, U.username, sum(S.score) as score_sum " +
                "from ( user U inner join score S on U.id = S.user_id  ) " +
                "group by S.user_id order by score_sum desc" );
    }

    /*
     * A query for getting the leaderboard for past 7 days
     */
    @Override
    public List<Map<String, String>> getLeaderboardfor7days() {
        return generateResponse( "select U.id, U.username, sum(S.score) as score_sum " +
                "from( user U inner join score S on U.id = S.user_id  ) " +
                "where S.timestamp >= date_sub(now(), interval 7 day)" +
                "group by S.user_id order by score_sum desc" );
    }

    /*
     * An helper function for generic response creating from the raw database queries.
     */
    private List<Map<String, String>> generateResponse( String query_string ){
        Query query = entityManager
                .createNativeQuery(query_string);
        List<Object[]> result = query.getResultList();
        List<Map<String,String>> hashmapList = new ArrayList<>();

        for( int i=0; i<result.size(); i++ ){
            hashmapList.add( new HashMap<>() );
            for( int j=0; j<3; j++ ){
                hashmapList.get(i).put( key_array[j], result.get(i)[j].toString() );
            }
        }
        return hashmapList;
    }
}
