package Controller;

import Model.stockDatabase;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

//This is the Controller in the MVC pattern
public class automatedCheckoutSystem {

    public static ArrayList<String> dBRead(ArrayList<String> dBCall) {
        stockDatabase controlDB = new stockDatabase();
        controlDB.readStock();
        dBCall.add(controlDB.getStock().toString());
        return dBCall;
        //dBRead().add(controlDB.getStock().toString());
        //return dBRead();
        //ArrayList<String> dBArray = controlDB.getStock();
        //return dBArray;
    }

    /*public int requestSearchDB(int searchID) {
        stockDatabase controlDB = new stockDatabase();
        controlDB.searchDB(searchID);
        return searchID;
    }*/

    /*public basketStock(int itemID) {
        stockDatabase itemDB = new stockDatabase();
        itemDB.readStock();
        ArrayList<String> basket = itemDB.getStock();

    }*/

    /*public static int adminLogin (String uName, String pWord, int auth) {
        String aUsername = "admin";
        String aPassword = "password";
        if (uName == aUsername && pWord == aPassword) {
           auth = 1;
        }
        return auth;
    }*/

    public void writeDB (String sName, String sPrice, String sQuantity) throws IOException {
        stockDatabase wDB = new stockDatabase();
        wDB.readStock();
        int dBLength = wDB.lengthStock();
        Integer writeID = dBLength / 4 + 1;
        String sID = writeID.toString();
        wDB.writeFile(sID);
        wDB.writeFile(sName);
        wDB.writeFile(sPrice);
        wDB.writeFile(sQuantity);
    }
}
