package pro.bashkatov.pokerapi.model.security.dto;

public class UserTokenDto {
    private String token;

    public UserTokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
