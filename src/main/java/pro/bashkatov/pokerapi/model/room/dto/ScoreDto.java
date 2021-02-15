package pro.bashkatov.pokerapi.model.room.dto;

import lombok.Value;

@Value
public class ScoreDto {
    String Name;
    int Score;

    public ScoreDto(String name, int score) {
        Name = name;
        Score = score;
    }
}
