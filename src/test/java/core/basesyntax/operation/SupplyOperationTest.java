package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SupplyOperationTest {

    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();

        Storage.setFruitQuantity("banana", 0);
        Storage.setFruitQuantity("apple", 0);
    }

    @Test
    void apply_validSupply_addsQuantity() {
        Storage.setFruitQuantity("banana", 20);
        FruitTransaction transaction =
                new FruitTransaction(Operation.SUPPLY, "banana", 30);

        supplyOperation.apply(transaction);

        assertEquals(50, Storage.getFruitQuantity("banana"));
    }

    @Test
    void apply_supplyToNonExistingFruit_createsNewEntry() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.SUPPLY, "mango", 15);

        supplyOperation.apply(transaction);

        assertEquals(15, Storage.getFruitQuantity("mango"));
    }

    @Test
    void apply_zeroQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.SUPPLY, "apple", 0);

        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.apply(transaction));
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.SUPPLY, "apple", -10);

        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.apply(transaction));
    }

    @Test
    void apply_nullTransaction_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.apply(null));
    }

    @Test
    void apply_nullFruit_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.SUPPLY, null, 10);

        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.apply(transaction));
    }
}
