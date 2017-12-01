package com.fma.laundryapp.helper;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class HelperCurrency
{
    public static final Locale LOCALE = new Locale("id", "ID");
    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(LOCALE);

    public static String format(double paramDouble)
    {
        return numberFormat.format(paramDouble);
    }

    public static String format(long paramLong)
    {
        return numberFormat.format(paramLong);
    }


    public static Double revert(String paramString)
            throws ParseException
    {
        return Double.valueOf(numberFormat.parse(paramString).doubleValue());
    }
}