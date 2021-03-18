package pro.bashkatov.pokerapi.model.security.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class CredentialsDto {
    private String login;
    private String password;
}
