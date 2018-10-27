package com.ceng453.Server;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


@Transactional
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, String> authenticate(String username, String password) throws NoSuchAlgorithmException {
        Map<String, String> dictionary = new HashMap<String, String>();

        Query query = entityManager
                .createNativeQuery("select * from user where username=?", User.class);
        query.setParameter(1, username);

        User result = null;
        try {
            result = (User) query.getSingleResult();
        } catch(  NoResultException ex){
            dictionary.put("Error_code", "2");
            dictionary.put("Error_text", "No users found");
            return dictionary;
        }

        if( result.getPassword_encrypted().equals( EncryptionHelper.encrypt(password) ) ) {
            String generated_token = EncryptionHelper.generateToken();
            User real_user = entityManager.getReference(User.class, result.getId());
            real_user.setToken(generated_token);
            entityManager.merge(real_user);
            dictionary.put("Success", "1");
            dictionary.put("Token", generated_token);
        }
        else{
            dictionary.put("Error_code", "1");
            dictionary.put("Error_text", "Incorrect Password");
        }

        return dictionary;
    }
}
