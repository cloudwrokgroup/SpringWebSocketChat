/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class UserService {
    
    private HashMap<String, String> users = new HashMap();
    
    @Autowired
    private SimpMessagingTemplate template;
    
    public void addUser(String id, String name){
        this.users.put(id,name);
        this.getUsers();
    }
    
    public void getUsers(){
        this.template.convertAndSend("/users", users.values());
    }
    public void deleteUser(String key){
        this.users.remove(key);
        this.getUsers();
    }
    
}
