package pro.bashkatov.pokerapi.model.room.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserDto {
    String Name;

    public UserDto(@JsonProperty("name") String name) {
        Name = name;
    }
}
