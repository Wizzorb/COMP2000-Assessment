package Model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

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

    @AfterEach
    void tearDown() {
        System.out.println("** AFTER **");
    }

    @AfterAll
    public static void tearDownAfter() {
        System.out.println("** AFTER CLASS **");
    }
}