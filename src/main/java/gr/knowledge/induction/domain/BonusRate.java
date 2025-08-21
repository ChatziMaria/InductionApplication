package gr.knowledge.induction.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public enum BonusRate {
    WINTER("Winter", 1.3),
    AUTUMN("Autumn", 0.4),
    SPRING("Spring",0.6 ),
    SUMMER("Summer", 0.7);


    BonusRate(String season, Double rate) {
        this.season = season;
        this.rate = rate;
    }

    public String getSeason() {
        return season;
    }

    public Double getRate() {
        return rate;
    }

    private final String season;
    private final  Double rate;


    public static BonusRate getRateBySeason(String season) {
        for (BonusRate rate : BonusRate.values()) {
            if (rate.getSeason().equalsIgnoreCase(season)) {
                return rate;
            }
        }
        throw new IllegalArgumentException();
    }
}