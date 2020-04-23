package org.sang.websocket_wechat.controller;

import org.sang.websocket_wechat.model.Chat;
import org.sang.websocket_wechat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class GreetingController {
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) throws Exception {
        return message;
    }
    @MessageMapping("/chat")
    public void chat(Principal principal, Chat chat) {
        String from =principal.getName();
        chat.setFrom(from);
        messagingTemplate.convertAndSendToUser(chat.getTo(),
                "/queue/chat", chat);
    }
}