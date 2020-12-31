package View;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AdminUITest {

    @BeforeAll
    public static void setUpBefore() {
        System.out.println("** BEFORE ALL **");
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testRead() {

    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    public static void tearDownAfter() {
        System.out.println("** AFTER CLASS **");
    }
}