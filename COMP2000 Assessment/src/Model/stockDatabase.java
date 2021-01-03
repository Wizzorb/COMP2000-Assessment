package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

//This is the Model in the MVC design pattern.
public class stockDatabase {
    //public String[][] Stock = { {"0", "BlimBlam", "0.99"} };

    //ArrayList<ArrayList<String>> Stock = new ArrayList<ArrayList<String>>(0);
    //ArrayList<String> sEntry = new ArrayList<>();

    ArrayList<String> Stock = new ArrayList<>();
    public Integer sID;
    public String sName;
    public Float sPrice;
    public Integer sQuantity;
    File sFile = new File("COMP2000 Assessment/src/Model/StockFile.txt");

    //public List<Controller.automatedCheckoutSystem> automated Checkout System = new ArrayList<Controller.automatedCheckoutSystem> ();

    public void updateStock(String newSName, String newSPrice, String newSQuantity) {
//begin of modifiable zone(JavaCode)......C/876e8404-9140-4678-b7a0-ad44ef962fc6
        //Integer aPosition = Stock.length + 1;

        //Stock[aPosition][0] = newSID;
        //Stock[aPosition][1] = newSName;
        //Stock[aPosition][2] = newSPrice;

        //sEntry.add(newSID);
        //sEntry.add(newSName);
        //sEntry.add(newSPrice);
        //Stock.add(sEntry);
//end of modifiable zone(JavaCode)........E/876e8404-9140-4678-b7a0-ad44ef962fc6
    }

    public void readStock() {
        /*try {
            int count = 0;
            Scanner stockReader = new Scanner(sFile);
            while (stockReader.hasNextLine()) {
                String data = stockReader.nextLine();
                if (count == 3) {
                    count = 0;
                    sEntry.add(data);
                    Stock.add(sEntry);
                } else {
                    sEntry.add(data);
                    count++;
                }
            }
            stockReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        */

        try {
            Scanner stockReader = new Scanner(sFile);
            while (stockReader.hasNextLine()) {
                String data = stockReader.nextLine();
                Stock.add(data);
            }
            stockReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void searchDB(int searchID) {
        readStock();
        sID = Stock.indexOf(searchID);
        sName = Stock.get(sID + 1);
        sPrice = sPrice.parseFloat(Stock.get(sID + 2));
        sQuantity = Integer.parseInt(Stock.get(sID + 3));
    }

    public ArrayList<String> getStock() {
        return Stock;
    }

}
