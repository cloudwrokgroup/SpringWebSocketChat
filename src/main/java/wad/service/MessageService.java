package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wad.domain.Message;

@Service
public class MessageService {

    @Autowired
    private Poliitikko poliitikko;
    
    @Autowired 
    private RoBotti robo;
    
    @Autowired 
    RacistBot rasisti;

    @Autowired
    private SimpMessagingTemplate template;

    public void addMessage(Message message) {
        this.template.convertAndSend("/channel/" + message.getChannel(), message);
        
    }

    // lähettää viestejä 10 sekunnin välein default-kanavalle
    @Scheduled(fixedDelay = 600000)
    public void send() {
    this.template.convertAndSend("/channel/default", rasisti.getMessage());
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
