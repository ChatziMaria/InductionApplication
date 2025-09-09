package gr.knowledge.induction.enums;

public enum BonusRate {
    WINTER( Seasons.WINTER, 1.3),
    AUTUMN( Seasons.AUTUMN, 0.4),
    SPRING( Seasons.SPRING,0.6 ),
    SUMMER( Seasons.SUMMER, 0.7);


    BonusRate(Seasons season, Double rate) {
        this.season = season;
        this.rate = rate;
    }

    public Seasons getSeason() {
        return season;
    }

    public Double getRate() {
        return rate;
    }

    private final Seasons season;
    private final  Double rate;


    public static BonusRate getRateBySeason(Seasons season) {
        for (BonusRate rate : BonusRate.values()) {
            if (rate.getSeason() == season) {
                return rate;
            }
        }
        throw new IllegalArgumentException();
    }
}