package ru.ifmo.ailab.e3soos.facts;

/**
 *
 * @author Maxim Kolchin
 */
public enum ElementType {

    BASIC, FAST, CORRECTION, WIDE_ANGULAR;

    public static ElementType parse(final String value) {
        try {
            return valueOf(value);
        } catch(IllegalArgumentException ex) {
            if(value.length() == 1) {
                if(value.equalsIgnoreCase("b")){
                    return BASIC;
                } else if(value.equalsIgnoreCase("t")) {
                    return FAST;
                } else if(value.equalsIgnoreCase("c")) {
                    return CORRECTION;
                } else if(value.equalsIgnoreCase("y")){
                    return WIDE_ANGULAR;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public String toShortName() {
        if(this == BASIC) {
            return "B";
        } else if(this == FAST) {
            return "T";
        } else if(this == CORRECTION) {
            return "C";
        } else if(this == WIDE_ANGULAR) {
            return "Y";
        }
        return "B";
    }
}
