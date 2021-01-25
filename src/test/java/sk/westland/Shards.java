package sk.westland;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Shards {

    double[] prices = new double[] {1.2, 2, 3.2, 4, 6, 8, 10, 12, 16, 20};


    @Test
    void numberFormating() {

        DecimalFormat df = new DecimalFormat("#,###.00");

        double number = 324561231.23;

        String reformat = df.format(number);
        String ref = reformat.replace(',', '.').replaceAll(" ", ",");
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(3);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(3);

        df.setCurrency(Currency.getInstance(Locale.GERMANY));
        String format = df.format(number);
        System.out.println("Format: " + format +" , " + ref + " , " + String.format("%,d", (int) number));
        System.out.println(ref);

    }

    @Test
    void test() {
        for (int i = 0; i < prices.length; i++) {
            double price = prices[i];

            System.out.println("Cena: " + price + ", Shardy: " + getShards(i));

        }
        int a = getShards(3)*2;
        int b = getShards(5);
        if(a > b)
            System.out.println("\n WARN: price shard (2*4E: " + a + ", 8E:" + b + ")");
    }

    private int getShards(int id) {
        if(prices.length-1 < id)
            return 0;

        double price = prices[id];
        int base = (int) (price * 100);
        int mulitplier = 16 * id - 16;
        return  (base + (mulitplier-id));

        // cena * 100 + ( 16 * poradie - 16)
    }
}
