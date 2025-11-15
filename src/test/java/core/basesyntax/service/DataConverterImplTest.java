package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataConverterImplTest {

    private final DataConverter converter = new DataConverterImpl();

    @Test
    void convertToTransaction_validInput_parsesCorrectly() {
        List<String> lines = new ArrayList<>();
        lines.add("operation,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("s,apple,10");

        List<FruitTransaction> result = converter.convertToTransaction(lines);

        assertEquals(2, result.size());

        FruitTransaction t1 = result.get(0);
        assertEquals(Operation.BALANCE, t1.getOperation());
        assertEquals("banana", t1.getFruit());
        assertEquals(20, t1.getQuantity());

        FruitTransaction t2 = result.get(1);
        assertEquals(Operation.SUPPLY, t2.getOperation());
        assertEquals("apple", t2.getFruit());
        assertEquals(10, t2.getQuantity());
    }

    @Test
    void convertToTransaction_emptyLines_skipped() {
        List<String> lines = new ArrayList<>();
        lines.add("operation,fruit,quantity");
        lines.add("");
        lines.add(null);
        lines.add("s,orange,5");

        List<FruitTransaction> result = converter.convertToTransaction(lines);

        assertEquals(1, result.size());
        assertEquals("orange", result.get(0).getFruit());
    }

    @Test
    void convertToTransaction_invalidColumnsCount_throwsException() {
        List<String> lines = new ArrayList<>();
        lines.add("operation,fruit,quantity");
        lines.add("b,banana");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_invalidQuantityFormat_throwsException() {
        List<String> lines = new ArrayList<>();
        lines.add("operation,fruit,quantity");
        lines.add("b,banana,notANumber");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_negativeQuantity_throwsException() {
        List<String> lines = new ArrayList<>();
        lines.add("operation,fruit,quantity");
        lines.add("b,banana,-5");

        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_invalidOperation_throwsException() {
        List<String> lines = new ArrayList<>();
        lines.add("operation,fruit,quantity");
        lines.add("x,banana,10");

        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_nullInput_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(null));
    }
}
