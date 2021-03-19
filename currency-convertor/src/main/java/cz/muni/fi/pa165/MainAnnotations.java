package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("cz.muni.fi.pa165.currency");
        ctx.refresh();
        CurrencyConvertor currencyConvertor = ctx.getBean("currencyConvertor",
            CurrencyConvertorImpl.class);

        System.out.println(currencyConvertor.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("10")));
    }
}
