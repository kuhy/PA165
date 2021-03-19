package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        CurrencyConvertor currencyConvertor = applicationContext.getBean("currencyConvertor",
            CurrencyConvertorImpl.class);

        System.out.println(currencyConvertor.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("1")));
    }
}
