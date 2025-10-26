package org.example;

public enum EconomicProfile {
    AGRICULTURAL(Commodity.FOOD, Commodity.TEXTILES, Commodity.MACHINERY, Commodity.COMPUTERS),
    
    INDUSTRIAL(Commodity.MACHINERY, Commodity.COMPUTERS, Commodity.FOOD, Commodity.RARE_METALS),
    
    HIGH_TECH(Commodity.COMPUTERS, Commodity.MEDICINE, Commodity.FOOD, Commodity.TEXTILES),
    
    MINING(Commodity.RARE_METALS, Commodity.WATER, Commodity.MACHINERY, Commodity.MEDICINE);

    private final Commodity primaryExport;
    private final Commodity secondaryExport;
    private final Commodity primaryImport;
    private final Commodity secondaryImport;
    
    EconomicProfile(Commodity primaryExport, Commodity secondaryExport, Commodity primaryImport, Commodity secondaryImport) {
        this.primaryExport = primaryExport;
        this.secondaryExport = secondaryExport;
        this.primaryImport = primaryImport;
        this.secondaryImport = secondaryImport;
    }

    public Commodity getPrimaryExport() {return primaryExport;}
    public Commodity getSecondaryExport() {return secondaryExport;}
    public Commodity getPrimaryImport() {return primaryImport;}
    public Commodity getSecondaryImport() {return secondaryImport;}
}