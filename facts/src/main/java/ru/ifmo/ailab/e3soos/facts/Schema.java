package ru.ifmo.ailab.e3soos.facts;

/**
 *
 */
public class Schema {

    /**
     * A wide angular element.
     */
    private Element yElement;
    private Element cyElement;
    /**
     * A basic element.
     */
    private Element bElement;
    private Element cbElement;
    /**
     * A fast element.
     */
    private Element tElement;
    private Element ctElement;

    public Schema() {
    }

    public Schema(final Element b) {
        if (b.getElementType() != ElementType.B) {
            throw new IllegalArgumentException();
        }
        this.bElement = b;
    }

    public Element getCbElement() {
        return cbElement;
    }

    public void setCbElement(Element cbElement) {
        if(cbElement.getElementType() != ElementType.C){
            throw new IllegalArgumentException();
        }
        this.cbElement = cbElement;
    }

    public Element getCtElement() {
        return ctElement;
    }

    public void setCtElement(Element ctElement) {
        if(ctElement.getElementType() != ElementType.C){
            throw new IllegalArgumentException();
        }
        this.ctElement = ctElement;
    }

    public Element getCyElement() {
        return cyElement;
    }

    public void setCyElement(Element cyElement) {
        if(cyElement.getElementType() != ElementType.C){
            throw new IllegalArgumentException();
        }
        this.cyElement = cyElement;
    }

    public Element getYElement() {
        return yElement;
    }

    public void setYElement(final Element y) {
        if (y.getElementType() != ElementType.Y) {
            throw new IllegalArgumentException();
        }
        this.yElement = y;
    }

    public Element getBElement() {
        return bElement;
    }

    public void setBElement(final Element b) {
        if (b.getElementType() != ElementType.B) {
            throw new IllegalArgumentException();
        }
        this.bElement = b;
    }

    public Element getTElement() {
        return tElement;
    }

    public void setTElement(final Element t) {
        if (t.getElementType() != ElementType.T) {
            throw new IllegalArgumentException();
        }
        this.tElement = t;
    }

    @Override
    public String toString() {
        StringBuilder codeBuilder = new StringBuilder();
        //Wide-angular element
        if (yElement != null) {
            codeBuilder.append(yElement.toString());
            codeBuilder.append(" + ");
        }
        if (cyElement != null) {
            codeBuilder.append(cyElement.toString());
            codeBuilder.append(" + ");
        }
        //Basic element
        codeBuilder.append(bElement.toString());
        if (cbElement != null) {
            codeBuilder.append(" + ");
            codeBuilder.append(cbElement.toString());
        }
        //Fast element
        if (tElement != null) {
            codeBuilder.append(" + ");
            codeBuilder.append(tElement.toString());
        }
        if (ctElement != null) {
            codeBuilder.append(" + ");
            codeBuilder.append(ctElement.toString());
        }

        return codeBuilder.toString();
    }
}
