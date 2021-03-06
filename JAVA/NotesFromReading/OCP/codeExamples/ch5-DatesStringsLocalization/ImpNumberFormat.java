import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

class ImpNumberFormat{
    public static void main(String[] args) throws ParseException{
        // 1. create a NumberFormat object requiered.
        // 2. Use parse() or format() as per use case. format() to turn a number into a String. parse() to turn a String into a number

        // The format classes are not thread-safe.

        // Formatting.
        int attendeesPerYear = 3_200_000;
        int attendeesPerMonth = attendeesPerYear / 12;

        NumberFormat us = NumberFormat.getInstance(Locale.US);
        System.out.println(us.format(attendeesPerMonth));

        NumberFormat g = NumberFormat.getInstance(Locale.GERMANY);
        System.out.println(g.format(attendeesPerMonth));

        NumberFormat ca = NumberFormat.getInstance(Locale.CANADA_FRENCH);
        System.out.println(ca.format(attendeesPerMonth));

        NumberFormat usCcy = NumberFormat.getCurrencyInstance(Locale.US);
        int price = 58;
        System.out.println(usCcy.format(price));

        // Parsing.
        String str = "40.66";
        NumberFormat fr = NumberFormat.getInstance(Locale.FRANCE);
        System.out.println(us.parse(str));
        System.out.println(fr.parse(str)); // note output: The lesson is to make sure parsing is done using the right locale!

    }
}