package kz.adil.discountservice.service.impl;

import kz.adil.discountservice.model.user.User;
import kz.adil.discountservice.model.user.UserType;
import kz.adil.discountservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomUserDetailsService.class})
@ExtendWith(SpringExtension.class)
class MongoUserDetailsServiceTest {
    @Autowired
    private CustomUserDetailsService mongoUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link CustomUserDetailsService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        // Arrange
        when(this.userRepository.findByName(any())).thenReturn(mock(User.class));
        String username = "janedoe";

        // Act
        this.mongoUserDetailsService.loadUserByUsername(username);

        // Assert
        verify(this.userRepository).findByName(any());
    }

    /**
     * Method under test: {@link CustomUserDetailsService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        // Arrange
        when(this.userRepository.findByName(any())).thenThrow(new UsernameNotFoundException("Msg"));
        String username = "janedoe";

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> this.mongoUserDetailsService.loadUserByUsername(username));
        verify(this.userRepository).findByName(any());
    }

    /**
     * Method under test: {@link CustomUserDetailsService#getCurrentUser()}
     */
    @Test
    void testGetCurrentUser() {
        User user = mock(User.class);
        final List<UserType> userTypes = new ArrayList<>();
        userTypes.add(UserType.EMPLOYEE);
        when(user.getTypes()).thenReturn(userTypes);
        when(user.getCreatedDate()).thenReturn(LocalDate.now().minusYears(4));
        final SecurityContext securityContext = mock(SecurityContext.class);
        final Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            // Arrange and Act
            this.mongoUserDetailsService.getCurrentUser();
        }
        // Assert
        verify(authentication, times(1)).isAuthenticated();
        verify(securityContext, times(1)).getAuthentication();
    }

    /**
     * Method under test: {@link CustomUserDetailsService#getCurrentUser()}
     */
    @Test
    void testGetCurrentUser2() {
        User user = mock(User.class);
        final List<UserType> userTypes = new ArrayList<>();
        userTypes.add(UserType.EMPLOYEE);
        when(user.getTypes()).thenReturn(userTypes);
        when(user.getCreatedDate()).thenReturn(LocalDate.now().minusYears(4));
        final SecurityContext securityContext = mock(SecurityContext.class);
        final Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            // Arrange and Act
            this.mongoUserDetailsService.getCurrentUser();
        }
        // Assert
        verify(authentication, times(1)).isAuthenticated();
        verify(securityContext, times(2)).getAuthentication();
    }
}

