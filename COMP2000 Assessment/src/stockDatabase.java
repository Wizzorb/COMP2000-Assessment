import java.util.ArrayList;
import java.util.List;

//This is the Model in the MVC design pattern.
public class stockDatabase {
    //public String[][] Stock = { {"0", "BlimBlam", "0.99"} };
    ArrayList<ArrayList<String>> Stock = new ArrayList<ArrayList<String>>(3);
    ArrayList<String> sEntry = new ArrayList<>();
    public Integer sID;
    public String sName;
    public Float sPrice;

    //public List<automatedCheckoutSystem> automated Checkout System = new ArrayList<automatedCheckoutSystem> ();

    public void updateStock(String newSID, String newSName, String newSPrice) {
//begin of modifiable zone(JavaCode)......C/876e8404-9140-4678-b7a0-ad44ef962fc6
        //Integer aPosition = Stock.length + 1;

        //Stock[aPosition][0] = newSID;
        //Stock[aPosition][1] = newSName;
        //Stock[aPosition][2] = newSPrice;

        sEntry.add(newSID);
        sEntry.add(newSName);
        sEntry.add(newSPrice);
        Stock.add(sEntry);
//end of modifiable zone(JavaCode)........E/876e8404-9140-4678-b7a0-ad44ef962fc6
    }

}
