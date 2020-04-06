package com.quizapp.quizapp.security;

import com.quizapp.quizapp.dto.UserDto;
import com.quizapp.quizapp.entity.User;
import com.quizapp.quizapp.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) {
        final Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return user.map(MyUserDetails::new).get();
    }

    public static UserDto getLoggedInUserDetails() {
        final Object userDetails =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(userDetails instanceof MyUserDetails)) {
            return null;
        }
        final MyUserDetails user = (MyUserDetails) userDetails;
        return new UserDto(user.getId(), user.getUsername());
    }
}
