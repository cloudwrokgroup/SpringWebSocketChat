package wad.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.WebSocketDisconnectListener;
import wad.domain.Message;
import wad.service.MessageService;
import wad.service.UserService;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WebSocketDisconnectListener listener;
 

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @RequestParam("name") String username, @RequestParam("channel") String channel) {
       this.listener.addName(username);
        
        model.addAttribute("channel", channel);
        model.addAttribute("username", username);
        return "chat";
    }

    @MessageMapping("/messages")
    public void handleMessage(Message message) throws Exception {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        message.setTime(timeStamp);
        messageService.addMessage(message);
        userService.getUsers();
        System.out.println("apuva");
    }
    @MessageMapping("/getusers")
    public void loadUsers()throws Exception{
        userService.getUsers();
    }
    @MessageMapping("/close")
    public void closeChat(String name)throws Exception{
        System.out.println("Cloussaa se");                
    }
}
