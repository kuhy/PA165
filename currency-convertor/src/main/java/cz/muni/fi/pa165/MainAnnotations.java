package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("cz.muni.fi.pa165");
        ctx.refresh();
        CurrencyConvertor currencyConvertor = (CurrencyConvertor) ctx.getBean("currencyConvertor");

        System.out.println(currencyConvertor.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("1")));
    }
}
