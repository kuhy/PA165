package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        CurrencyConvertor currencyConvertor = (CurrencyConvertor) ctx.getBean("currencyConvertor");

        System.out.println(currencyConvertor.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("1")));
    }
}
