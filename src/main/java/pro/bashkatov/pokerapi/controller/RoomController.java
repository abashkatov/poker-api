package pro.bashkatov.pokerapi.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pro.bashkatov.pokerapi.model.room.dto.RoomTopicDto;
import pro.bashkatov.pokerapi.model.room.dto.ScoreDto;
import pro.bashkatov.pokerapi.model.room.dto.UserDto;

@Controller
public class RoomController {
    @MessageMapping("/room/im-here")
    @SendTo("/topic/room/im-here")
    public UserDto imHere(@Payload UserDto userDto) {
        return userDto;
    }

    @MessageMapping("/room/im-here-too")
    @SendTo("/topic/room/im-here-too")
    public UserDto imHereToo(@Payload UserDto userDto) {
        return userDto;
    }

    @MessageMapping("/room/topic-changed")
    @SendTo("/topic/room/topic-changed")
    public RoomTopicDto topicChanged(@Payload RoomTopicDto roomTopicDto) {
        return roomTopicDto;
    }

    @MessageMapping("/room/i-left")
    @SendTo("/topic/room/i-left")
    public UserDto iLeft(@Payload UserDto userDto) {
        return userDto;
    }

    @MessageMapping("/room/i-voted")
    @SendTo("/topic/room/i-voted")
    public UserDto iVoted(@Payload UserDto userDto) {
        return userDto;
    }

    @MessageMapping("/room/open-cards")
    @SendTo("/topic/room/open-cards")
    public void openCards() {
    }

    @MessageMapping("/room/my-score")
    @SendTo("/topic/room/my-score")
    public ScoreDto openCards(@Payload ScoreDto scoreDto) {
        return scoreDto;
    }
}
