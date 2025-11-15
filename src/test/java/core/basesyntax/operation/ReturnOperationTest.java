package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {

    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();

        Storage.setFruitQuantity("banana", 0);
        Storage.setFruitQuantity("apple", 0);
    }

    @Test
    void apply_validReturn_addsQuantity() {
        Storage.setFruitQuantity("banana", 20);
        FruitTransaction transaction =
                new FruitTransaction(Operation.RETURN, "banana", 10);

        returnOperation.apply(transaction);

        assertEquals(30, Storage.getFruitQuantity("banana"));
    }

    @Test
    void apply_returnToNonExistingFruit_createsNewEntry() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.RETURN, "mango", 7);

        returnOperation.apply(transaction);

        assertEquals(7, Storage.getFruitQuantity("mango"));
    }

    @Test
    void apply_zeroQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.RETURN, "apple", 0);

        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.apply(transaction));
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.RETURN, "apple", -3);

        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.apply(transaction));
    }

    @Test
    void apply_nullTransaction_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.apply(null));
    }

    @Test
    void apply_nullFruit_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.RETURN, null, 5);

        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.apply(transaction));
    }
}
