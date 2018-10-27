package com.ceng453.Server;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, String> authenticate(String username, String password) throws NoSuchAlgorithmException {
        Map<String, String> dictionary = new HashMap<String, String>();

        Query query = entityManager
                .createNativeQuery("select * from user where username=?", User.class);
        query.setParameter(1, username);

        User result = (User)query.getSingleResult();

        if( result.getPassword_encrypted().equals( EncryptionHelper.encrypt(password) ) )
            dictionary.put("Result","result");
        else
            dictionary.put("Error", "Error Code 1 : Auth Failed"); // Maybe migrate to a better structure

        return dictionary;
    }
}
