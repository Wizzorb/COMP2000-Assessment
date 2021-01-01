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

    @AfterEach
    void tearDown() {
        System.out.println("** AFTER **");
    }

    @AfterAll
    public static void tearDownAfter() {
        System.out.println("** AFTER CLASS **");
    }
}