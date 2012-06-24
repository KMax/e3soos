package ru.ifmo.ailab.e3soos.facts;

/**
 *
 */
public enum SurfaceType {

    O, P, A, F, I, V;

    public static SurfaceType parse(final String value) {
        try {
            return valueOf(value);
        } catch(IllegalArgumentException ignored) {
            if(value.length() == 1) {
                if(value.equalsIgnoreCase("o")){
                    return O;
                } else if(value.equalsIgnoreCase("p")) {
                    return P;
                } else if(value.equalsIgnoreCase("a")) {
                    return A;
                } else if(value.equalsIgnoreCase("f")) {
                    return F;
                } else if(value.equalsIgnoreCase("i")) {
                    return I;
                } else if(value.equalsIgnoreCase("v")) {
                    return V;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public String toShortName() {
        return name();
    }
}
