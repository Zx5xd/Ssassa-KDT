package web.ssa.enumf;

import lombok.Getter;

@Getter
public enum RAMBandwith {
    DDR4_2400("2400MHz","PC4-19200"),
    DDR4_2666("2666MHz","PC4-21300"),
    DDR4_3200("3200MHz","PC4-25600"),
    DDR4_3600("3600MHz","PC4-28800"),
    DDR5_5600("5600MHz","PC5-44800"),
    DDR5_6000("6000MHz","PC5-48000"),
    DDR5_6400("6400MHz","PC5-51200"),
    DDR5_7600("7600MHz","PC5-60800");

    private final String bandwidth;
    private final String ramClock;

    RAMBandwith(String bandwidth, String ramClock) {
        this.bandwidth = bandwidth;
        this.ramClock = ramClock;
    }


}
