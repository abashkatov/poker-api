package pro.bashkatov.pokerapi.model.chat.dto;

import lombok.Value;

@Value
public class MessageDto {
    String Author;
    String Message;

    public MessageDto(String author, String message) {
        Author = author;
        Message = message;
    }
}
