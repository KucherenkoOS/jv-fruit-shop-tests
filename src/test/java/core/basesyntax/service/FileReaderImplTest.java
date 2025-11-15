package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileReaderImplTest {
    private FileReader fileReader;
    private static final String VALID_FILE = "src/test/resources/ReadTest.csv";

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_ok() {
        List<String> lines = fileReader.read(VALID_FILE);

        assertNotNull(lines);
        assertEquals(4, lines.size());
        assertEquals("operation,fruit,quantity", lines.get(0));
        assertEquals("s,banana,20", lines.get(1));
        assertEquals("b,apple,5", lines.get(2));
        assertEquals("r,banana,3", lines.get(3));
    }

    @Test
    void read_nullPath_notOk() {
        assertThrows(IllegalArgumentException.class, () -> fileReader.read(null));
    }

    @Test
    void read_nonExistingFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read("no_such_file.csv"));
    }
}
