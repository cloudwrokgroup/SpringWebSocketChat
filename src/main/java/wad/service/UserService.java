/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.util.ArrayList;
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
    
    private ArrayList<String> users = new ArrayList();
    
    @Autowired
    private SimpMessagingTemplate template;
    
    public void addUser(String name){
        this.users.add(name);
    }
    
    public void getUsers(){
        this.template.convertAndSend("/users", users);
    }
    
}
