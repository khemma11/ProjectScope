package am.itspace.projectscope.security;

import am.itspace.projectscope.model.User;
import am.itspace.projectscope.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByEmail(email);

        if (!byUsername.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CurrentUser(byUsername.get());
    }
}
