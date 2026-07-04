package com.cinema.strategy;

/**
 * Provides the pricing strategy used by the application.
 * <p>
 * This class centralizes the creation of {@link PricingStrategy}
 * instances, making it easier to change the application's pricing
 * policy without affecting client code.
 */
public class PricingStrategyProvider {

    /**
     * Returns the pricing strategy currently used by the application.
     *
     * @return the active {@link PricingStrategy} implementation
     */
    public static PricingStrategy getStrategy() {
        return new NormalPricingStrategy();
    }
}