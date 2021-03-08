package pro.bashkatov.pokerapi.entity;

import org.springframework.data.annotation.Id;

public class Room {
    @Id private String id;
    private String title;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Room setTitle(String title) {
        this.title = title;
        return this;
    }
}
