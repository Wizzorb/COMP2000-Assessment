package Controller;

import Model.*;

import java.io.IOException;
import java.util.ArrayList;

//This is the Controller in the MVC pattern
public class automatedCheckoutSystem {

    public static ArrayList<String> dBRead(ArrayList<String> dBCall) {  //Method call the read method in stockDatabase, and returns database entirety.
        stockDatabase controlDB = new stockDatabase();
        controlDB.readStock();
        dBCall.add(controlDB.getStock().toString());
        return dBCall;
        //dBRead().add(controlDB.getStock().toString());
        //return dBRead();
        //ArrayList<String> dBArray = controlDB.getStock();
        //return dBArray;
    }

    public void writeDB (String sName, String sPrice, String sQuantity) throws IOException {    //Send the written details into the database.
        stockDatabase wDB = new stockDatabase();
        wDB.readStock();
        int dBLength = wDB.lengthStock();
        Integer writeID = dBLength / 4 + 1;
        String sID = writeID.toString();
        wDB.writeFile(wDB.sFile, sID);
        wDB.writeFile(wDB.sFile, sName);
        wDB.writeFile(wDB.sFile, sPrice);
        wDB.writeFile(wDB.sFile, sQuantity);
    }

    public void removeDB (String sID) throws IOException {  //Sends remove entry info to database.
        stockDatabase rDB = new stockDatabase();
        rDB.searchDB(Integer.parseInt(sID));
        rDB.removeFile(rDB.sID, rDB.sName, rDB.sPrice, rDB.sQuantity);
    }

    public String nameSearchDB (String search) {    //Sends name search info to database, adn returns the results to the UI.
        stockDatabase nsDB = new stockDatabase();
        nsDB.readStock();
        ArrayList<String> nsList = nsDB.getStock();
        Integer nsNamePos = nsList.indexOf(search.toLowerCase());
        String nsID = nsList.get(nsNamePos - 1);
        return nsID;
    }

    public void changeQuantityDB (String itemID, Integer qChange, boolean plusMinus) throws IOException {   //Sends info to change quantity.
        stockDatabase cQDB = new stockDatabase();
        cQDB.dBQuantityChange(itemID, qChange, plusMinus);
    }

    public String currencyConversion(String currency) {     //Converts an integer into currency format, e.g. 13.99.
        StringBuilder cCBuilder = new StringBuilder(currency);
        Integer pos = currency.length() - 2;
        cCBuilder.insert(pos, ".");
        return cCBuilder.toString();

        /*if (currency.length() <= 2) {
            String newCurrency = "£0." + currency;
            return newCurrency;
        } else {
            String newCurrency = "£" + currency;
            StringBuilder cCBuilder = new StringBuilder(currency);
            Integer pos = newCurrency.length() - 3;
            cCBuilder.insert(pos, ".");
            return cCBuilder.toString();
        }*/
    }

    public int getTotal (ArrayList<Integer> items) {    //Returns total of the price.
        stockDatabase tDB = new stockDatabase();
        tDB.priceTotal(items);
        return Integer.parseInt(tDB.getSPriceTotal());
    }

    public boolean adminCheck (String aUsername, String aPassword) {    //Sends entered username and password to adminUser model to compare.
        adminUser admin = new adminUser();
        if (aUsername.equals(admin.getUsername()) & aPassword.equals(admin.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    /*public int getChange (String totalPrice, String amountCash) {
        Integer tP = Integer.parseInt(totalPrice);
        Integer aC = Integer.parseInt(amountCash);
        Integer change = aC - tP;
        return change;
    }*/

    /*public ArrayList<String> basketAdd (ArrayList<String> basket, Integer searchID) {
        stockDatabase bDB = new stockDatabase();
        bDB.readStock();
        bDB.searchDB(searchID);
        if (bDB.sName == basket.get(2)) {
            bD
        }
    }*/

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
}
