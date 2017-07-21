package sales.pg.adapters;

/**
 * Created by USER on 20-07-2017.
 */

public class Dealers {
    String dealername,dealercode;
    public Dealers(String dealercode , String dealername){
        this.dealercode=dealercode;this.dealername = dealername;
    }

    public String getDealercode() {
        return dealercode;
    }

    public String getDealername() {
        return dealername;
    }
}
