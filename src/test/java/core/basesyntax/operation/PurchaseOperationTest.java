package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.PurchaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseOperationTest {

    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();

        Storage.setFruitQuantity("banana", 0);
        Storage.setFruitQuantity("apple", 0);
    }

    @Test
    void apply_validPurchase_updatesStorage() {
        Storage.setFruitQuantity("banana", 30);
        FruitTransaction transaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 10);

        purchaseOperation.apply(transaction);

        assertEquals(20, Storage.getFruitQuantity("banana"));
    }

    @Test
    void apply_notEnoughFruit_throwsException() {
        Storage.setFruitQuantity("banana", 5);
        FruitTransaction transaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 10);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperation.apply(transaction));

        assertTrue(exception.getMessage().contains("Not enough banana"));
    }

    @Test
    void apply_nullTransaction_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.apply(null));
    }

    @Test
    void apply_nullFruitName_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.PURCHASE, null, 5);

        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.apply(transaction));
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.PURCHASE, "apple", -5);

        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.apply(transaction));
    }

    @Test
    void apply_zeroQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.PURCHASE, "apple", 0);

        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.apply(transaction));
    }
}
