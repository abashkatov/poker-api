package pro.bashkatov.pokerapi.controller.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pro.bashkatov.pokerapi.entity.User;
import pro.bashkatov.pokerapi.model.chat.dto.MessageDto;
import pro.bashkatov.pokerapi.model.security.dto.CredentialsDto;
import pro.bashkatov.pokerapi.model.security.dto.UserTokenDto;
import pro.bashkatov.pokerapi.model.security.service.TokenProvider;
import pro.bashkatov.pokerapi.model.security.service.UserService;

@RestController
public class SecurityController {
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public SecurityController(
            UserService userService,
            TokenProvider tokenProvider,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/registration")
    @ResponseBody
    public UserTokenDto registration(@RequestBody CredentialsDto credentialsDto) {
        User user = new User(
                credentialsDto.getLogin(),
                bCryptPasswordEncoder.encode(credentialsDto.getPassword())
        );
        if(!userService.saveUser(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong credentials");
        }

        return new UserTokenDto(tokenProvider.getJwtToken(user));
    }

    @GetMapping("/secure-endpoint")
    @ResponseBody
    public MessageDto secureEndpoint() {
        return new MessageDto("author", "message");
    }

    @GetMapping("/unsecure-endpoint")
    @ResponseBody
    public MessageDto unsecureEndpoint() {
        return new MessageDto("author", "message");
    }
}
