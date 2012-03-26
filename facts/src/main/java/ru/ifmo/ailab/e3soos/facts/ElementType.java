package ru.ifmo.ailab.e3soos.facts;

/**
 *
 * @author Maxim Kolchin
 */
public enum ElementType {

    B, // Basic element
    T, // Fast element
    C, // Correction element
    Y; // Wide-angular element

    public static ElementType parse(final String value) {
        try {
            return valueOf(value);
        } catch(IllegalArgumentException ex) {
            if(value.length() == 1) {
                if(value.equalsIgnoreCase("b")){
                    return B;
                } else if(value.equalsIgnoreCase("t")) {
                    return T;
                } else if(value.equalsIgnoreCase("c")) {
                    return C;
                } else if(value.equalsIgnoreCase("y")){
                    return Y;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public String toShortName() {
        if(this == B) {
            return "B";
        } else if(this == T) {
            return "T";
        } else if(this == C) {
            return "C";
        } else if(this == Y) {
            return "Y";
        }
        return "B";
    }
}
