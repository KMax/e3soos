package ru.ifmo.ailab.e3soos.facts;

public class Classification {

    private int j;
    private int w;
    private int f;
    private int l;
    private int q;
    private int s;
    private int d;

    public int getJ() {
        return j;
    }

    public void setJ(final int j) {
        this.j = j;
    }

    public int getW() {
        return w;
    }

    public void setW(final int w) {
        this.w = w;
    }

    public int getF() {
        return f;
    }

    public void setF(final int f) {
        this.f = f;
    }

    public int getL() {
        return l;
    }

    public void setL(final int l) {
        this.l = l;
    }

    public int getQ() {
        return q;
    }

    public void setQ(final int q) {
        this.q = q;
    }

    public int getS() {
        return s;
    }

    public void setS(final int s) {
        this.s = s;
    }

    public int getD() {
        return d;
    }

    public void setD(final int d) {
        this.d = d;
    }

    public int getR() {
        return d + f + j + l + q + s + w;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Classification) {
            Classification other = (Classification) obj;
            if (this.d != other.d) {
                return false;
            }
            if (this.f != other.f) {
                return false;
            }
            if (this.j != other.j) {
                return false;
            }
            if (this.l != other.l) {
                return false;
            }
            if (this.q != other.q) {
                return false;
            }
            if (this.s != other.s) {
                return false;
            }
            if (this.w != other.w) {
                return false;
            }
            if (this.hashCode() != other.hashCode()) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.j;
        hash = 47 * hash + this.w;
        hash = 47 * hash + this.f;
        hash = 47 * hash + this.l;
        hash = 47 * hash + this.q;
        hash = 47 * hash + this.s;
        hash = 47 * hash + this.d;
        return hash;
    }

    @Override
    public String toString() {
        return "[J:" + j + ",W:" + w + ",F:" + f + ",L:" + l + ",Q:" + q
                + ",S:" + s + ",D:" + d + ",R:" + getR() + "]";
    }
}
