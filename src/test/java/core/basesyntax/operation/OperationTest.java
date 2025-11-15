package core.basesyntax.operation;

import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationTest {

    @Test
    void fromCode_validCodes_returnsCorrectOperation() {
        assertEquals(Operation.BALANCE, Operation.fromCode("b"));
        assertEquals(Operation.SUPPLY, Operation.fromCode("s"));
        assertEquals(Operation.PURCHASE, Operation.fromCode("p"));
        assertEquals(Operation.RETURN, Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidCode_throwsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Operation.fromCode("x")
        );

        Assertions.assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void fromCode_nullCode_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> Operation.fromCode(null));
    }
}
