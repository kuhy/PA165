package cz.muni.fi.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logger.trace("Converting {} {} to {}.", sourceAmount, sourceCurrency, targetCurrency);
        if (sourceCurrency == null) {
            logger.error("Source currency cannot be null.");
            throw new IllegalArgumentException("Source currency cannot be null.");
        }
        if (targetCurrency == null) {
            logger.error("Target currency cannot be null.");
            throw new IllegalArgumentException("Target currency cannot be null.");
        }
        if (sourceAmount == null) {
            logger.error("Source amount cannot be null.");
            throw new IllegalArgumentException("Source amount cannot be null.");
        }
        try {
            BigDecimal exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if (exchangeRate == null) {
                String warning = "Exchange rate from " + sourceCurrency + " to " + targetCurrency + " is not known.";
                logger.warn(warning);
                throw new UnknownExchangeRateException(warning);
            }
            return exchangeRate.multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
        } catch (ExternalServiceFailureException e) {
            String errorMessage = "Lookup for current exchange rate failed.";
            logger.error(errorMessage, e);
            throw new UnknownExchangeRateException(errorMessage, e);
        }
    }
}
