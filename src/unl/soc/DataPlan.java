package unl.soc;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Objects;

/**
 * The DataPlan class represents a data plan item.
 * It extends the Item class and includes additional fields specific to data plans.
 * It includes Getters, ToString, HashCode and Equals methods
 */
@XStreamAlias("dataPlan")
public class DataPlan extends Item{
    @XStreamOmitField
    private static final double TAX_PERCENTAGE = 0.055;
    @XStreamOmitField
    private double totalGB;

    public DataPlan(String uniqueCode, String name, double pricePerGB) {
        super(uniqueCode, name, pricePerGB);
    }

    public DataPlan(Item item, double totalGB) {
        super(item.getUniqueCode(), item.getName(), item.getBasePrice());
        this.totalGB = totalGB;
    }

    public double getPricePerGB() {
        return super.getBasePrice();
    }

    public double getTotalGB() {
        return totalGB;
    }

    @Override
    public double getGrossPrice() { return super.getBasePrice() * totalGB; }

    @Override
    public double getTotalTax() { return getGrossPrice() * TAX_PERCENTAGE; }

    @Override
    public String toString() {
        return String.format("%s - %s \n %20.2f GB @ $%5.2f / GB \n %60s %9.2f $%9.2f", getName() + " (" + getUniqueCode() + ")", "Data", getTotalGB(), getPricePerGB(), "$",getTotalTax(), getGrossPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPlan dataPlan = (DataPlan) o;
        return Double.compare(super.getBasePrice(), dataPlan.getBasePrice()) == 0 && Double.compare(totalGB, dataPlan.totalGB) == 0 && Double.compare(getGrossPrice(), dataPlan.getGrossPrice()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getBasePrice(), totalGB, getGrossPrice());
    }
}