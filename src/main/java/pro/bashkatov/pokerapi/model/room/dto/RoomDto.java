package pro.bashkatov.pokerapi.model.room.dto;

import lombok.Value;
import java.util.UUID;

@Value
public class RoomDto {
    UUID uuid;
    String title;

    public RoomDto(UUID uuid, String title) {
        this.uuid = uuid;
        this.title = title;
    }
}
