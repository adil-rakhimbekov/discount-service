package kz.adil.discountservice.migration;

import kz.adil.discountservice.repository.DiscountRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class V002DiscountsTest {
    /**
     * Method under test: {@link V002Discounts#rollback()}
     */
    @Test
    void testRollback() {
        // Arrange
        DiscountRepository discountRepository = mock(DiscountRepository.class);
        doNothing().when(discountRepository).deleteAll();
        V002Discounts v002Discounts = new V002Discounts(discountRepository);

        // Act
        v002Discounts.rollback();

        // Assert
        verify(discountRepository).deleteAll();
    }
}

