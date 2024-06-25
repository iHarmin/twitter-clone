package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.service.Twitter2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class Twitter2ServiceImpl implements Twitter2Service {

    @Autowired
    Twitter2Repository twitter2Repository;

    @Override
    public String createUser(Twitter2 twitter2) {
        if(!twitter2.getEmail().contains("@dal.ca")) {
            return "Invalid email address";
        }
        twitter2Repository.save(twitter2);
        return "User profile created";
    }

    @Override
    public String updatePassword(Twitter2 twitter2) {
        Optional<Twitter2> pass = twitter2Repository.findById(twitter2.getId());
        if(pass.isPresent()) {
            Twitter2 p1 = pass.get();
            p1.setPassword(twitter2.getPassword());
            return "Password updated successfully";
        } else {
            return "Password not updated";
        }
    }

}
