package com.cinema.strategy;

public class PricingStrategyProvider {

    public static PricingStrategy getStrategy() {

        return new NormalPricingStrategy();
    }
}
