package kz.adil.discountservice.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kz.adil.discountservice.model.user.User;
import kz.adil.discountservice.model.user.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class UserTest {
    /**
     * Method under test: {@link User#getAuthorities()}
     */
    @Test
    void testGetAuthorities() {
        // Arrange
        LocalDate createdDate = LocalDate.ofEpochDay(1L);
        User user = new User("Name", "iloveyou", createdDate, new ArrayList<>());

        // Act
        Collection<? extends GrantedAuthority> actualAuthorities = user.getAuthorities();

        // Assert
        assertTrue(actualAuthorities.isEmpty());
    }

    /**
     * Method under test: {@link User#getAuthorities()}
     */
    @Test
    void testGetAuthorities2() {
        // Arrange
        ArrayList<UserType> userTypeList = new ArrayList<>();
        userTypeList.add(UserType.EMPLOYEE);
        User user = new User("Name", "iloveyou", LocalDate.ofEpochDay(1L), userTypeList);

        // Act
        Collection<? extends GrantedAuthority> actualAuthorities = user.getAuthorities();

        // Assert
        assertEquals(1, actualAuthorities.size());
        assertEquals("EMPLOYEE", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).getAuthority());
    }

    /**
     * Method under test: {@link User#getAuthorities()}
     */
    @Test
    void testGetAuthorities3() {
        // Arrange
        ArrayList<UserType> userTypeList = new ArrayList<>();
        userTypeList.add(UserType.EMPLOYEE);
        userTypeList.add(UserType.EMPLOYEE);
        User user = new User("Name", "iloveyou", LocalDate.ofEpochDay(1L), userTypeList);

        // Act
        Collection<? extends GrantedAuthority> actualAuthorities = user.getAuthorities();

        // Assert
        assertEquals(2, actualAuthorities.size());
        assertEquals("EMPLOYEE", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).toString());
        assertEquals("EMPLOYEE", ((List<? extends GrantedAuthority>) actualAuthorities).get(1).toString());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User(String, String, LocalDate, java.util.List)}
     *   <li>{@link User#getUsername()}
     *   <li>{@link User#isAccountNonExpired()}
     *   <li>{@link User#isAccountNonLocked()}
     *   <li>{@link User#isCredentialsNonExpired()}
     *   <li>{@link User#isEnabled()}
     * </ul>
     */
    @Test
    void testConstructor() {
        // Arrange
        String name = "Name";
        String password = "iloveyou";
        LocalDate createdDate = LocalDate.ofEpochDay(1L);
        ArrayList<UserType> types = new ArrayList<>();

        // Act
        User actualUser = new User(name, password, createdDate, types);

        // Assert
        assertEquals("Name", actualUser.getUsername());
        assertTrue(actualUser.isAccountNonExpired());
        assertTrue(actualUser.isAccountNonLocked());
        assertTrue(actualUser.isCredentialsNonExpired());
        assertTrue(actualUser.isEnabled());
    }
}

