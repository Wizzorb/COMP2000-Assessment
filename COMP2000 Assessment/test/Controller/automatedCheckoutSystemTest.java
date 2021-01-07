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
        String tName = "test2";
        String tPrice = "1.51";
        String tQuantity = "1451";
        aCSTest.writeDB(tName, tPrice, tQuantity);
        stockDatabase aCSTestDB = new stockDatabase();
        aCSTestDB.readStock();
        System.out.println(aCSTestDB.getStock());
    }

    @Test
    public void testRemove() throws IOException {
        String tID = "3";
        aCSTest.removeDB(tID);
        stockDatabase removeTestDB = new stockDatabase();
        removeTestDB.readStock();
        System.out.println(removeTestDB.getStock());
    }

    @Test
    public void testCurrency() {
        String tC = aCSTest.currencyConversion("199");
        System.out.println(tC);
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