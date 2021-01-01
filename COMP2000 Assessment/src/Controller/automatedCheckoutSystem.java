package Controller;

import Model.*;
import View.KioskUI;
import java.util.*;

//This is the Controller in the MVC pattern
public class automatedCheckoutSystem {

    public static ArrayList<String> dBRead(ArrayList<String> dBArray) {
        stockDatabase controlDB = new stockDatabase();
        controlDB.readStock();
        dBArray = controlDB.getStock();
        return dBArray;
    }

    /*public basketStock(int itemID) {
        stockDatabase itemDB = new stockDatabase();
        itemDB.readStock();
        ArrayList<String> basket = itemDB.getStock();

    }*/
}
