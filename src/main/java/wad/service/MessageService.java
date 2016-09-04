package wad.service;

import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wad.domain.Message;
import wad.domain.User;

@Service
public class MessageService {

    
    @Autowired
    private Poliitikko poliitikko;
    @Autowired 
    private RoBotti robo;
    @Autowired 
    private RacistBot rasisti;
    @Autowired
    private AdminBot adminBot;
    
    @Autowired
    private UserService userService;
    

    @Autowired
    private SimpMessagingTemplate template;

    public void addMessage(Message message) {
        this.template.convertAndSend("/channel/" + message.getChannel(), message);
    }
    

    public void handleCommand(Message message) throws IOException{
        String content = message.getContent().trim();
        
        if(content.equals("/RasistiBot kerro vitsi"))
            this.send();
        if(content.equals("/AdminBot clear uploads"))
            adminBot.clearImages();
    }
    
    public void send() {
        this.template.convertAndSend("/channel/default", rasisti.getMessage());
    }
    
    @PostConstruct
    public void init(){
        //userService.addUser("ad112mi112nbot", new User(adminBot.getName()));
    }
    
    
  /*  @Scheduled(fixedDelay = 10000)
    public void send() {
        System.out.println("Poliitiko lähettää");
        this.template.convertAndSend("/channel/default", poliitikko.getMessage());
    }*/
    
  /*  @Scheduled(fixedDelay=20000)
    public void sendM(){
        this.template.convertAndSend("/channel/default",robo.getMessage());
    }*/
}
