package pro.bashkatov.pokerapi.controller.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pro.bashkatov.pokerapi.entity.User;
import pro.bashkatov.pokerapi.model.security.dto.CredentialsDto;
import pro.bashkatov.pokerapi.model.security.dto.UserTokenDto;
import pro.bashkatov.pokerapi.model.security.service.TokenProvider;
import pro.bashkatov.pokerapi.repository.UserRepository;

@RestController
public class SecurityController {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public SecurityController(
            UserRepository userRepository,
            TokenProvider tokenProvider
    ) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/registration")
    @ResponseBody
    public UserTokenDto registration(@RequestBody CredentialsDto credentialsDto) {
        if(userRepository.countByUsername(credentialsDto.getLogin()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong credentials");
        }
        User user = new User(credentialsDto.getLogin(), credentialsDto.getPassword());
        userRepository.save(user);

        return new UserTokenDto(tokenProvider.getJwtToken(user));
    }}
