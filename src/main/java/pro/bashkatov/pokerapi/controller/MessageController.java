package pro.bashkatov.pokerapi.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pro.bashkatov.pokerapi.model.chat.dto.MessageDto;

@Controller
public class MessageController {
    @MessageMapping("/message")
    @SendTo("/topic/chat")
    public MessageDto greeting(@Payload MessageDto message) {
        return message;
    }
}
