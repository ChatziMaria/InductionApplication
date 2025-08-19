package gr.knowledge.induction.domain;

import java.math.BigDecimal;

public enum BonusRate {
    WINTER("Winter", 1.3),
    AUTUMN("Autumn", 0.4),
    SPRING("Spring",0.6 ),
    SUMMER("Summer", 0.7);


    private final String season;
    private final  Double rate;

    BonusRate(String season, Double rate){
        this.season = season;
        this.rate = rate;
    }

    // Getter for season
    public String getSeason() {
        return season;
    }

    // Getter for rate
    public double getRate() {
        return rate;
    }

}
