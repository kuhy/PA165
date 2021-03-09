package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
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

    @Test
    public void testConvertWithNullSourceCurrency() {
        fail("Test is not implemented yet.");
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        fail("Test is not implemented yet.");
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        fail("Test is not implemented yet.");
    }

    @Test
    public void testConvertWithUnknownCurrency() {
        fail("Test is not implemented yet.");
    }

    @Test
    public void testConvertWithExternalServiceFailure() {
        fail("Test is not implemented yet.");
    }

}
