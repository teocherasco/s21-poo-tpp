package org.example.domain.enums;

public enum CupSize {
    CHICO(1), MEDIANO(2), GRANDE(3);

    private final int maxScoops;

    CupSize(int maxScoops) {
        this.maxScoops = maxScoops;
    }

    public int getMaxScoops() {
        return maxScoops;
    }
}

