package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

public class CurrencyConvertorImplTest {
    @Mock
    CurrencyConvertorImpl currencyConvertorMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(currencyConvertorMock.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal("1")))
            .thenReturn(new BigDecimal("26.42"));
        when(currencyConvertorMock.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal("0")))
            .thenReturn(new BigDecimal("0"));
        when(currencyConvertorMock.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal("2")))
            .thenReturn(new BigDecimal("52.84"));
        when(currencyConvertorMock.convert(Currency.getInstance("USD"), Currency.getInstance("CZK"), new BigDecimal("1")))
            .thenReturn(new BigDecimal("22.26"));
        when(currencyConvertorMock.convert(Currency.getInstance("USD"), Currency.getInstance("CZK"), new BigDecimal("2")))
            .thenReturn(new BigDecimal("44.53"));
        when(currencyConvertorMock.convert(Currency.getInstance("USD"), Currency.getInstance("CZK"), new BigDecimal("-1")))
            .thenReturn(new BigDecimal("-22.26"));
        when(currencyConvertorMock.convert(Currency.getInstance("USD"), Currency.getInstance("CZK"), new BigDecimal("1.5")))
            .thenReturn(new BigDecimal("33.40"));
        when(currencyConvertorMock.convert((Currency) isNull(), (Currency) notNull(), (BigDecimal) notNull()))
            .thenThrow(new IllegalArgumentException());
        when(currencyConvertorMock.convert((Currency) notNull(), (Currency) isNull(), (BigDecimal) notNull()))
            .thenThrow(new IllegalArgumentException());
        when(currencyConvertorMock.convert((Currency) notNull(), (Currency) notNull(), (BigDecimal) isNull()))
            .thenThrow(new IllegalArgumentException());
        when(currencyConvertorMock.convert(Currency.getInstance("EUR"), Currency.getInstance("CHF"), new BigDecimal("1")))
            .thenThrow(new UnknownExchangeRateException("CHF is not known currency."));
        when(currencyConvertorMock.convert(Currency.getInstance("EUR"), Currency.getInstance("USD"), new BigDecimal("1")))
            .thenThrow(new UnknownExchangeRateException("Unknown exchange rate for given pair."));
    }

    @Test
    public void testConvert() {
        assertEquals(new BigDecimal("26.42"), currencyConvertorMock.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("1")));
        assertEquals(new BigDecimal("0"), currencyConvertorMock.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("0")));
        assertEquals(new BigDecimal("52.84"), currencyConvertorMock.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("2")));
        assertEquals(new BigDecimal("22.26"), currencyConvertorMock.convert(Currency.getInstance("USD"),
            Currency.getInstance("CZK"), new BigDecimal("1")));
        assertEquals(new BigDecimal("44.53"), currencyConvertorMock.convert(Currency.getInstance("USD"),
            Currency.getInstance("CZK"), new BigDecimal("2")));
        assertEquals(new BigDecimal("-22.26"), currencyConvertorMock.convert(Currency.getInstance("USD"),
            Currency.getInstance("CZK"), new BigDecimal("-1")));
        assertEquals(new BigDecimal("33.40"), currencyConvertorMock.convert(Currency.getInstance("USD"),
            Currency.getInstance("CZK"), new BigDecimal("1.5")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() {
        currencyConvertorMock.convert(null, Currency.getInstance("CZK"), new BigDecimal("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() {
        currencyConvertorMock.convert(Currency.getInstance("EUR"), null, new BigDecimal("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() {
        currencyConvertorMock.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() {
        currencyConvertorMock.convert(Currency.getInstance("EUR"), Currency.getInstance("CHF"), new BigDecimal("1"));
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithExternalServiceFailure() {
        currencyConvertorMock.convert(Currency.getInstance("EUR"), Currency.getInstance("USD"), new BigDecimal("1"));
    }

}
