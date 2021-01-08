package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//This is the Model in the MVC design pattern.
public class stockDatabase {
    //public String[][] Stock = { {"0", "BlimBlam", "0.99"} };

    //ArrayList<ArrayList<String>> Stock = new ArrayList<ArrayList<String>>(0);
    //ArrayList<String> sEntry = new ArrayList<>();

    ArrayList<String> Stock = new ArrayList<>();
    public String sID;
    public String sName = " ";
    public String sPrice = "0.0";
    public String sQuantity = "0";
    public int sPriceTotal;
    public File sFile = new File("COMP2000 Assessment/src/Model/StockFile.txt");

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

    public void searchDB(Integer searchID) {
        try {
            readStock();
            sID = searchID.toString();
            Integer index = Stock.indexOf(sID);
            Integer tempName = index + 1;
            Integer tempPrice = index + 2;
            Integer tempQuantity = index + 3;
            sName = Stock.get(tempName);
            sPrice = Stock.get(tempPrice);
            sQuantity = Stock.get(tempQuantity);

            //int posName = Stock.indexOf((searchID + 1));
            //int posPrice = Stock.indexOf((searchID + 2));
            //int postQuantity = Stock.indexOf((searchID + 3));
            //sID = searchID;
            //sName = Stock.get(searchID).trim();
            //sPrice = Stock.get(searchID + 1).trim();
            //sQuantity = Stock.get(searchID + 2).trim();
        } catch (NumberFormatException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeFile(File wFile, String newS) throws IOException {
        FileWriter write = new FileWriter(wFile, true);
        PrintWriter printLine = new PrintWriter(write);
        printLine.printf("%s" + "%n", newS);
        printLine.close();
    }

    public void removeFile(String dID, String dName, String dPrice, String dQuantity) throws IOException {
        File tempFile = new File("COMP2000 Assessment/src/Model/tempFile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine;
            if (trimmedLine.equals(dID)) continue;
            else if (trimmedLine.equals(dName)) continue;
            else if (trimmedLine.equals(dPrice)) continue;
            else if (trimmedLine.equals(dQuantity)) continue;
            //writer.close();
            writer.write(currentLine + System.lineSeparator());
            //writeFile(sFile, currentLine);
        }
        writer.close();
        reader.close();
        sFile.delete();
        boolean successful = tempFile.renameTo(sFile);
    }

    public ArrayList<String> getStock() {
        return Stock;
    }

    public void priceTotal(ArrayList<Integer> prices) {
        for (int i = 0; i < prices.size(); i++) {
            sPriceTotal += prices.get(i);
        }
    }

    public int getSPriceTotal() {
        return sPriceTotal;
    }

    public int lengthStock() {
        int lStock = Stock.size();
        return lStock;
    }

}
