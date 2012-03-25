package ru.ifmo.ailab.e3soos.facts;

/**
 *
 * @author Maxim Kolchin
 */
public class Schema {

    /**
     * A wide angular element.
     */
    private Element yElement;

    /**
     * A basic element.
     */
    private Element bElement;

    /**
     * A fast element.
     */
    private Element tElement;

    public Schema() {

    }

    public Schema(final Element b) {
        if(b.getElementType() != ElementType.BASIC) {
            throw new IllegalArgumentException();
        }
        this.bElement = b;
    }

    public Element getYElement() {
        return yElement;
    }

    public void setYElement(final Element y) {
        if(y.getElementType() != ElementType.WIDE_ANGULAR) {
            throw new IllegalArgumentException();
        }
        this.yElement = y;
    }

    public Element getBElement() {
        return bElement;
    }

    public void setBElement(final Element b) {
        if(b.getElementType() != ElementType.BASIC) {
            throw new IllegalArgumentException();
        }
        this.bElement = b;
    }

    public Element getTElement() {
        return tElement;
    }

    public void setTElement(final Element t) {
        if(t.getElementType() != ElementType.FAST) {
            throw new IllegalArgumentException();
        }
        this.tElement = t;
    }

    @Override
    public String toString() {
        StringBuilder codeBuilder = new StringBuilder();
        if(yElement != null) {
            codeBuilder.append(yElement.toString());
            codeBuilder.append(" + ");
        }
        codeBuilder.append(bElement.toString());
        if(tElement != null) {
            codeBuilder.append(" + ");
            codeBuilder.append(tElement.toString());
        }

        return codeBuilder.toString();
    }

}
