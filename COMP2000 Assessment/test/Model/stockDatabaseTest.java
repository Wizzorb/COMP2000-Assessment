package Model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class stockDatabaseTest {
    stockDatabase testDB;

    @BeforeAll
    public static void setUpBefore() {
        System.out.println("** BEFORE ALL **");
    }

    @BeforeEach
    void setUp() {
        System.out.println("** BEFORE **");
        testDB = new stockDatabase();
    }

    @Test
    public void testDBRead() {
        ArrayList<String> testArray = new ArrayList<>();
        testDB.readStock();
        testArray.add(testDB.Stock.toString());
        System.out.println(testArray);
    }

    @Test
    public void testDBScan() {
        testDB.readStock();
        testDB.searchDB(2);
        try {
            System.out.println(testDB.sID);
            System.out.println(testDB.sName);
            System.out.println(testDB.sPrice);
            System.out.println(testDB.sQuantity);
        } catch (NumberFormatException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Test
    public void testPriceTotal() {
        ArrayList<Integer> testPrice = new ArrayList<Integer>();
        testPrice.add(100);
        testPrice.add(200);
        testPrice.add(520);
        testDB.priceTotal(testPrice);
        System.out.println(testDB.getSPriceTotal());
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