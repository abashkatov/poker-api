package pro.bashkatov.pokerapi.model.chat.dto;

public class MessageDto {
    private String Author;
    private String Message;

    public MessageDto(String author, String message) {
        Author = author;
        Message = message;
    }

    public String getAuthor() {
        return Author;
    }

    public String getMessage() {
        return Message;
    }
}
