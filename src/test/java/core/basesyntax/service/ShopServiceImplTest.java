package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    @Test
    void constructor_nullOperationStrategy_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new ShopServiceImpl(null));
    }

    @Test
    void process_nullTransactionList_notOk() {
        OperationStrategy strategy = new TestStrategy();
        ShopService shopService = new ShopServiceImpl(strategy);

        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(null));
    }

    @Test
    void process_nullTransaction_notOk() {
        OperationStrategy strategy = new TestStrategy();
        ShopService shopService = new ShopServiceImpl(strategy);

        List<FruitTransaction> list = new ArrayList<>();
        list.add(null);

        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(list));
    }

    @Test
    void process_nullOperation_notOk() {
        OperationStrategy strategy = new TestStrategy();
        ShopService shopService = new ShopServiceImpl(strategy);

        FruitTransaction tx = new FruitTransaction(null, "banana", 10);
        List<FruitTransaction> list = List.of(tx);

        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(list));
    }

    @Test
    void process_noHandler_notOk() {
        OperationStrategy emptyStrategy = operation -> null;

        ShopService shopService = new ShopServiceImpl(emptyStrategy);

        FruitTransaction tx = new FruitTransaction(Operation.PURCHASE, "banana", 10);

        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(List.of(tx)));
    }

    @Test
    void process_valid_callsHandler() {
        TestOperationHandler handler = new TestOperationHandler();

        TestStrategy strategy = new TestStrategy();
        strategy.addHandler(Operation.PURCHASE, handler);

        ShopService shopService = new ShopServiceImpl(strategy);

        FruitTransaction tx = new FruitTransaction(Operation.PURCHASE, "apple", 5);

        shopService.process(List.of(tx));

        assertEquals(1, handler.getCalls());
        assertEquals(tx, handler.getLastTransaction());

    }

    static class TestOperationHandler implements OperationHandler {
        private int calls = 0;
        private FruitTransaction lastTransaction;

        @Override
        public void apply(FruitTransaction transaction) {
            calls++;
            lastTransaction = transaction;
        }

        public int getCalls() {
            return calls;
        }

        public FruitTransaction getLastTransaction() {
            return lastTransaction;
        }
    }

    static class TestStrategy implements OperationStrategy {
        private final Map<Operation, OperationHandler> handlers =
                new EnumMap<>(Operation.class);

        void addHandler(Operation op, OperationHandler handler) {
            handlers.put(op, handler);
        }

        @Override
        public OperationHandler get(Operation operation) {
            return handlers.get(operation);
        }
    }
}
