package Controller;

import Model.stockDatabase;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class automatedCheckoutSystemTest {
    automatedCheckoutSystem aCSTest;

    @BeforeAll
    public static void setUpBefore() {
        System.out.println("** BEFORE ALL **");
    }

    @BeforeEach
    void setUp() {
        System.out.println("** BEFORE **");
        aCSTest = new automatedCheckoutSystem();
    }

    @Test
    public void testWrite() throws IOException {
        String tName = "test";
        String tPrice = "1.5";
        String tQuantity = "1";
        aCSTest.writeDB(tName, tPrice, tQuantity);
        stockDatabase aCSTestDB = new stockDatabase();
        aCSTestDB.readStock();
        System.out.println(aCSTestDB.getStock());
    }

    @AfterEach
    void tearDown() {
        System.out.println("** AFTER **");
    }

    @AfterAll
    public static void tearDownAfter() {
        System.out.println("** AFTER CLASS **");
    }
}