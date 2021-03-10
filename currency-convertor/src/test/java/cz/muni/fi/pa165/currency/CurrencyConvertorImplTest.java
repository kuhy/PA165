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

    private CurrencyConvertor currencyConvertor;

    @Mock
    private ExchangeRateTable exchangeRateTableMock;

    @Before
    public void setUp() throws ExternalServiceFailureException {
        MockitoAnnotations.initMocks(this);

        when(exchangeRateTableMock.getExchangeRate(Currency.getInstance("EUR"), Currency.getInstance("CZK")))
            .thenReturn(new BigDecimal("26.42"));
        when(exchangeRateTableMock.getExchangeRate(Currency.getInstance("USD"), Currency.getInstance("CZK")))
            .thenReturn(new BigDecimal("22.264"));
        when(exchangeRateTableMock.getExchangeRate(Currency.getInstance("EUR"), Currency.getInstance("CHF")))
            .thenReturn(null);
        when(exchangeRateTableMock.getExchangeRate(Currency.getInstance("EUR"), Currency.getInstance("USD")))
            .thenThrow(new ExternalServiceFailureException("Lookup for current exchange rate failed."));

        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTableMock);
    }

    @Test
    public void testConvert() {
        assertEquals(new BigDecimal("26.42"), currencyConvertor.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("1")));
        assertEquals(new BigDecimal("0.00"), currencyConvertor.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("0")));
        assertEquals(new BigDecimal("52.84"), currencyConvertor.convert(Currency.getInstance("EUR"),
            Currency.getInstance("CZK"), new BigDecimal("2")));
        assertEquals(new BigDecimal("22.26"), currencyConvertor.convert(Currency.getInstance("USD"),
            Currency.getInstance("CZK"), new BigDecimal("1")));
        assertEquals(new BigDecimal("44.53"), currencyConvertor.convert(Currency.getInstance("USD"),
            Currency.getInstance("CZK"), new BigDecimal("2")));
        assertEquals(new BigDecimal("-22.26"), currencyConvertor.convert(Currency.getInstance("USD"),
            Currency.getInstance("CZK"), new BigDecimal("-1")));
        assertEquals(new BigDecimal("33.40"), currencyConvertor.convert(Currency.getInstance("USD"),
            Currency.getInstance("CZK"), new BigDecimal("1.5")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() {
        currencyConvertor.convert(null, Currency.getInstance("CZK"), new BigDecimal("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() {
        currencyConvertor.convert(Currency.getInstance("EUR"), null, new BigDecimal("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() {
        currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() {
        currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CHF"), new BigDecimal("1"));
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithExternalServiceFailure() {
        currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("USD"), new BigDecimal("1"));
    }

}
