package unl.soc.items;

import java.time.LocalDateTime;

public class VoicePlan extends Item {

    private static final double TAX_PERCENTAGE = 0.065;
    private LocalDateTime timePeriod; //Target Time - Purchase time.
    private double priceBeforeTax;


    public VoicePlan(String uniqueCode, String type, String name, double basePrice) {
        super(uniqueCode, type, name, basePrice);
    }
}