package pl.fintech.metisfinancialcalculator.fincalcservice.enums;

public enum XDateType {

    YEAR("YEARS"), MONTH("MONTH"), WEEK("WEEK"), DAY("DAY");

    private final String dateype;

    XDateType(final String datetype) {
        this.dateype = datetype;
    }

    @Override
    public String toString() {
        return dateype;
    }
}
