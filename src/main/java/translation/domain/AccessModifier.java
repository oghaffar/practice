package translation.domain;

public enum AccessModifier {
    P("Public"), X("Private"), V("View"), I("Inherit");

    private final String longName;

    AccessModifier(String longName) {
        this.longName = longName;
    }

    public static String toLongName(final String shortName) {
        try {
            return AccessModifier.valueOf(shortName.toUpperCase()).longName;
        } catch (NullPointerException npe) {
            throw new RuntimeException("shortName is NULL!");
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("'" + shortName + "' is an invalid access modifier!");
        }
    }

    public static String toShortName(final String longName) {
        if (longName == null) {
            throw new RuntimeException("longName is NULL!");
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
