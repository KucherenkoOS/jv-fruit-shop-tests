package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void constructor_nullMap_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new OperationStrategyImpl(null));
    }

    @Test
    void get_validOperation_returnsCorrectHandler() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationHandler supplyHandler = new SupplyOperation();

        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, balanceHandler);
        handlers.put(Operation.SUPPLY, supplyHandler);

        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);

        assertEquals(balanceHandler, strategy.get(Operation.BALANCE));
        assertEquals(supplyHandler, strategy.get(Operation.SUPPLY));
    }

    @Test
    void get_operationNotInMap_returnsNull() {
        OperationHandler balanceHandler = new BalanceOperation();
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, balanceHandler);

        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);

        assertNull(strategy.get(Operation.PURCHASE));
    }
}
