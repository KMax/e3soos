package ru.ifmo.ailab.e3soos.facts;

/**
 *
 * @author Maxim Kolchin
 */
public class Element {

    private ElementType elementType;
    private int firstSurfaceZone;
    private SurfaceType firstSurfaceType;
    private int secondSurfaceZone;
    private SurfaceType secondSurfaceType;

    public Element() {
    }

    public Element(final ElementType type, int firstZone,
            final SurfaceType firstSurface, int secondZone,
            final SurfaceType secondSurface) {
        this();
        this.elementType = type;
        this.firstSurfaceZone = firstZone;
        this.firstSurfaceType = firstSurface;
        this.secondSurfaceZone = secondZone;
        this.secondSurfaceType = secondSurface;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public SurfaceType getFirstSurfaceType() {
        return firstSurfaceType;
    }

    public SurfaceType getSecondSurfaceType() {
        return secondSurfaceType;
    }

    public int getFirstSurfaceZone() {
        return firstSurfaceZone;
    }

    public int getSecondSurfaceZone() {
        return secondSurfaceZone;
    }

    public void setElementType(ElementType type) {
        this.elementType = type;
    }

    public void setFirstSurfaceType(SurfaceType typeOfFirstSurface) {
        this.firstSurfaceType = typeOfFirstSurface;
    }

    public void setSecondSurfaceType(SurfaceType typeOfSecondSurface) {
        this.secondSurfaceType = typeOfSecondSurface;
    }

    public void setFirstSurfaceZone(int zoneOfFirstSurface) {
        this.firstSurfaceZone = zoneOfFirstSurface;
    }

    public void setSecondSurfaceZone(int zoneOfSecondSurface) {
        this.secondSurfaceZone = zoneOfSecondSurface;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof Element) {
            Element other = (Element) obj;
            if(this.elementType != other.elementType) {
                return false;
            }
            if(this.firstSurfaceZone != other.firstSurfaceZone) {
                return false;
            }
            if(this.firstSurfaceType != other.firstSurfaceType) {
                return false;
            }
            if(this.secondSurfaceZone != other.secondSurfaceZone) {
                return false;
            }
            if(this.secondSurfaceType != other.secondSurfaceType) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash
                + (this.elementType != null ? this.elementType.hashCode() : 0);
        hash = 31 * hash + this.firstSurfaceZone;
        hash = 31 * hash + (this.firstSurfaceType != null
                ? this.firstSurfaceType.hashCode() : 0);
        hash = 31 * hash + this.secondSurfaceZone;
        hash = 31 * hash + (this.secondSurfaceType != null
                ? this.secondSurfaceType.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append(elementType.toShortName());
        codeBuilder.append(firstSurfaceZone);
        codeBuilder.append(firstSurfaceType.toShortName());
        codeBuilder.append(secondSurfaceZone);
        codeBuilder.append(secondSurfaceType.toShortName());

        return  codeBuilder.toString();
    }
}
