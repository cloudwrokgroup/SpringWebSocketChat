/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import wad.domain.User;

/**
 *
 * @author Asus
 */
@Service
public class UserService {
    
    private HashMap<String, User> users = new HashMap();
    
    @Autowired
    private SimpMessagingTemplate template;
    

    
    public void getUsers(){
        HashSet<String> userNames = new HashSet();
        for(User u : users.values()){
            userNames.add(u.getUsername());
        }
        this.template.convertAndSend("/users", userNames);
    }
    
    //for listener
    public void addUser(String id, User user){
        this.users.put(id,user);
        
        getUsers();
    }
    //for controller
    public User checkUser(User user){
        String tempIp = user.getIp();
        for(User u : users.values()){
            if(user.getIp().equals(u.getIp())){
                user.setUsername(u.getUsername());
                break;
            }
            if(user.getUsername().equals(u.getUsername())){
                user.setUsername("**TAKEN**");
            }
        }
        return user;
    }
    
    public void deleteUser(String key){
        this.users.remove(key);
        getUsers();
    }
    
    
    
}
