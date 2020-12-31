package Controller;

import Model.*;
import java.util.ArrayList;

//This is the Controller in the MVC pattern
public class automatedCheckoutSystem {

    public static ArrayList<String> dBRead(ArrayList<String> dBArray) {
        stockDatabase controlDB = new stockDatabase();
        controlDB.readStock(dBArray);
        return dBArray;
    }
}
