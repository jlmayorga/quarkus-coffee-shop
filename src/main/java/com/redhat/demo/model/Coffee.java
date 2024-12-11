package com.redhat.demo.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Coffee extends PanacheEntity {
    public static final int LOW_STOCK_THRESHOLD = 10;
    public static final BigDecimal PREMIUM_PRICE_THRESHOLD = new BigDecimal("5.00");

    @NotBlank(message = "Every coffee needs a name!")
    public String name;

    @NotNull(message = "What's coffee without a price?")
    @Positive(message = "Free coffee sounds great, but let's be realistic!")
    public BigDecimal price;

    public String roastLevel;

    @NotNull
    @Min(0)
    public Integer stock;

    public boolean needsRestock() {
        return stock < LOW_STOCK_THRESHOLD;
    }

    public boolean isPremium() {
        return price.compareTo(PREMIUM_PRICE_THRESHOLD) >= 0;
    }

    public static List<Coffee> findLowStock() {
        return list("stock < ?1", LOW_STOCK_THRESHOLD);
    }

    public static List<Coffee> findByRoastLevel(String roastLevel) {
        return list("roastLevel", roastLevel);
    }

    public static List<Coffee> findPremiumCoffees() {
        return list("price >= ?1", PREMIUM_PRICE_THRESHOLD);
    }
}