package org.example;

public enum Commodity {
    WATER("Water", 10),
    FOOD("Food", 20),
    TEXTILES("Textiles", 50),
    MACHINERY("Machinery", 200),
    MEDICINE("Medicine", 350),
    COMPUTERS("Computers", 500),
    RARE_METALS("Rare Metals", 1000);

    private final String name;
    private final int basePrice;

    Commodity(String name, int basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getName() { return name; }
    public int getBasePrice() { return basePrice; }
}