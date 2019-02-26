package translation.domain;

public enum AccessModifier {
    P("Public"), X("Private"), V("View"), I("Inherit");

    private final String longName;

    AccessModifier(String longName) {
        this.longName = longName;
    }

    public static String toLongName(final String shortName) throws NullPointerException {
        if (shortName == null) {
            throw new NullPointerException("shortName is NULL!");
        }

        try {
            return AccessModifier.valueOf(shortName.toUpperCase()).longName;
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("'" + shortName + "' is an invalid access modifier!");
        }
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return name();
    }

    public static String toShortName(final String longName) throws NullPointerException {
        if (longName == null) {
            throw new NullPointerException("longName is NULL!");
        }

        switch (longName.toUpperCase()) {
            case "PUBLIC": return P.name();
            case "PRIVATE": return X.name();
            case "VIEW": return V.name();
            case "INHERIT": return I.name();
            default:
                throw new RuntimeException("'" + longName + "' is an invalid access modifier!");
        }
    }

}
