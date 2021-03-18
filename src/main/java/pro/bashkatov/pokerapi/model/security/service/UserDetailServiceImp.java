package pro.bashkatov.pokerapi.model.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pro.bashkatov.pokerapi.entity.User;
import pro.bashkatov.pokerapi.repository.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
