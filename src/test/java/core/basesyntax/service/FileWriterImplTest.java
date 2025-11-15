package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileWriterImplTest {
    private FileWriter fileWriter;
    private static final String TEST_FILE = "src/test/resources/WriteTest.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator() + "banana,10";

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validData_ok() throws Exception {

        fileWriter.write(REPORT, TEST_FILE);

        String written = Files.readString(Path.of(TEST_FILE));

        assertEquals(REPORT, written);
    }

    @Test
    void write_nullReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(null, TEST_FILE),
                "Report cannot be null");
    }

    @Test
    void write_nullFilePath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("some data", null),
                "File path cannot be null");
    }

    @Test
    void write_invalidPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("data", "bad_folder/file.csv"));
    }
}
