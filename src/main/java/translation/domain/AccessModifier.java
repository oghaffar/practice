package translation.domain;

public enum AccessModifier {
    P("Public"), X("Private"), V("View"), I("Inherit");

    private final String expandedValue;

    AccessModifier(String expandedValue) {
        this.expandedValue = expandedValue;
    }

    public String toExpandedValue(final String contractedValue) {
        try {
            return AccessModifier.valueOf(contractedValue.toUpperCase()).expandedValue;
        } catch (NullPointerException npe) {
            throw new RuntimeException("Contracted Value is NULL!");
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException(contractedValue + " is not a valid modifier");
        }
    }

    public String toContractedValue(final String expandedValue) {
        if (expandedValue == null) {
            throw new RuntimeException("Expanded Value is NULL!");
        }

        switch (expandedValue.toUpperCase()) {
            case "PUBLIC": return P.name();
            case "PRIVATE": return X.name();
            case "VIEW": return V.name();
            case "INHERIT": return I.name();
            default:
                throw new RuntimeException(expandedValue + " is an Invalid Access Modifier!");
        }
    }

}
