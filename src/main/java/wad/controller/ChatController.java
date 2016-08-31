package wad.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import wad.WebSocketDisconnectListener;
import wad.domain.Message;
import wad.domain.User;
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
    
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request,Model model, @RequestParam("name") String username, @RequestParam("channel") String channel) {
        User user = new User();
        user.setIp(request.getRemoteAddr().toString());
        user.setUsername(username);
        
        user = userService.checkUser(user);
        this.listener.addUser(user);
        model.addAttribute("channel", channel);
        model.addAttribute("username", user.getUsername());
        return "chat";
    }
    
    @RequestMapping(value="/photo")
    @ResponseStatus(value = HttpStatus.OK)
    public void photo(MultipartFile file) throws IOException{
        
        System.out.println("PHOTO TULI");
        
        System.out.println(file.getBytes().length);
        if (!file.isEmpty()) {
                try {
                    String uploadsDir = "/uploads/";
                    String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
                    if(! new File(realPathtoUploads).exists()){
                        new File(realPathtoUploads).mkdir();
                    }
                    File dest = new File(realPathtoUploads + file.getOriginalFilename());
                    file.transferTo(dest);
                }catch(Exception e){
                    
                }
        }
    }
    


    @MessageMapping("/messages")
    public void handleMessage(Message message) throws Exception {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        message.setTime(timeStamp);
        System.out.println("MSG user: " + message.getUsername());
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
