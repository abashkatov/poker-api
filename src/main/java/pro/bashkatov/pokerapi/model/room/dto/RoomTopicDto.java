package pro.bashkatov.pokerapi.model.room.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class RoomTopicDto {
    String Title;

    public RoomTopicDto(@JsonProperty("title") String title) {
        Title = title;
    }
}
