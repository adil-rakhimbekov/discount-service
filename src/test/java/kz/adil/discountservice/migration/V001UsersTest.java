package kz.adil.discountservice.migration;

import kz.adil.discountservice.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class V001UsersTest {
    /**
     * Method under test: {@link V001Users#rollback()}
     */
    @Test
    void testRollback() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        doNothing().when(userRepository).deleteAll();
        V001Users v001Users = new V001Users(userRepository);

        // Act
        v001Users.rollback();

        // Assert
        verify(userRepository).deleteAll();
    }
}

