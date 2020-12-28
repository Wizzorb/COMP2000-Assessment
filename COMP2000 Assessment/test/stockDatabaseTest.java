import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class stockDatabaseTest {
    stockDatabase testDB;

    @BeforeAll
    public static void setUpBefore() {
        System.out.println("** BEFORE CLASS **");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("** BEFORE **");
        testDB = new stockDatabase();
    }

    @Test
    public void testDatabase() {

        //for (int i = 0; i < testDB.Stock.length; i++) {
        //    for (int j = 0; j < testDB.Stock[i].length; j++) {
        //        System.out.println(testDB.Stock[i][j]);
        //    }
        //}

        String tID = "0";
        String tName = "Poon";
        String tPrice = "420.69";

        testDB.updateStock(tID, tName, tPrice);
        for (int i = 0; i < testDB.Stock.size(); i++) {
            for (int j = 0; j < testDB.Stock.get(i).size(); j++) {
                System.out.println(testDB.Stock.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void testDatabaseAdd() {
        //String tID = Integer.toString(0);
        //String tName = "Jack";
        //String tPrice = Double.toString(420.69);
        //testDB.updateStock(tID, tName, tPrice);
        //for (int i = 0; i < testDB.Stock.length; i++) {
        //    for (int j = 0; j < testDB.Stock[i].length; j++) {
        //        System.out.println(testDB.Stock[i][j]);
        //    }
        //}
    }

    @AfterEach
    public void tearDown() {
        System.out.println("** AFTER **");
    }

    @AfterAll
    public static void tearDownAfter() {
        System.out.println("** AFTER CLASS **");
    }
}