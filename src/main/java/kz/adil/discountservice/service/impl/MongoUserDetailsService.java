package kz.adil.discountservice.service.impl;

import kz.adil.discountservice.model.User;
import kz.adil.discountservice.model.UserType;
import kz.adil.discountservice.repository.UserRepository;
import kz.adil.discountservice.service.UserRetrievingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoUserDetailsService implements UserDetailsService, UserRetrievingService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username);
    }

    @Override
    public User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return User.builder()
                .createdDate(LocalDate.now())
                .name("guest")
                .types(List.of(UserType.GUEST))
                .build();

    }
}
