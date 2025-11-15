package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();

        Storage.setFruitQuantity("banana", 0);
        Storage.setFruitQuantity("apple", 0);
    }

    @Test
    void apply_validBalance_setsValue() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, "banana", 40);

        balanceOperation.apply(transaction);

        assertEquals(40, Storage.getFruitQuantity("banana"));
    }

    @Test
    void apply_overwriteExistingBalance_updatesValue() {
        Storage.setFruitQuantity("apple", 10);

        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, "apple", 100);

        balanceOperation.apply(transaction);

        assertEquals(100, Storage.getFruitQuantity("apple"));
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, "banana", -5);

        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.apply(transaction));
    }

    @Test
    void apply_nullTransaction_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.apply(null));
    }

    @Test
    void apply_nullFruit_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, null, 10);

        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.apply(transaction));
    }
}
