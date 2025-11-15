package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportGeneratorImplTest {

    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();

        Storage.setFruitQuantity("banana", 0);
    }

    @Test
    void getReport_onlyOneFruit_returnsHeaderAndOneLine() {
        String expectedHeader = "fruit,quantity" + System.lineSeparator();
        String expectedLine = "banana,0" + System.lineSeparator();

        String report = reportGenerator.getReport();

        assertTrue(report.startsWith("fruit,quantity"));
        assertTrue(report.contains("banana,0" + System.lineSeparator()));
        assertEquals(expectedHeader + expectedLine, report);
    }

    @Test
    void getReport_multipleFruits_returnsAllLines() {
        Storage.setFruitQuantity("apple", 10);
        Storage.setFruitQuantity("orange", 5);

        String report = reportGenerator.getReport();

        assertTrue(report.startsWith("fruit,quantity"));
        assertTrue(report.contains("banana,0" + System.lineSeparator()));
        assertTrue(report.contains("apple,10" + System.lineSeparator()));
        assertTrue(report.contains("orange,5" + System.lineSeparator()));
    }
}
