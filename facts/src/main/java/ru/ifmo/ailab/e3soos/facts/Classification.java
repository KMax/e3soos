package ru.ifmo.ailab.e3soos.facts;

public class Classification {

    private int J;
    private int W;
    private int F;
    private int L;
    private int Q;
    private int S;
    private int D;

    public Classification() {
        this.J = 0;
        this.W = 0;
        this.F = 0;
        this.L = 0;
        this.Q = 0;
        this.S = 0;
        this.D = 0;
    }

    public Classification(int j, int w, int f, int l, int q, int s, int d) {
        this.J = j;
        this.W = w;
        this.F = f;
        this.L = l;
        this.Q = q;
        this.S = s;
        this.D = d;
    }

    public int getJ() {
        return J;
    }

    public void setJ(final int j) {
        J = j;
    }

    public int getW() {
        return W;
    }

    public void setW(final int w) {
        W = w;
    }

    public int getF() {
        return F;
    }

    public void setF(final int f) {
        F = f;
    }

    public int getL() {
        return L;
    }

    public void setL(final int l) {
        L = l;
    }

    public int getQ() {
        return Q;
    }

    public void setQ(final int q) {
        Q = q;
    }

    public int getS() {
        return S;
    }

    public void setS(final int s) {
        S = s;
    }

    public int getD() {
        return D;
    }

    public void setD(final int d) {
        D = d;
    }

    public int getR() {
        return D + F + J + L + Q + S + W;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Classification) {
            Classification other = (Classification) obj;
            if (this.D != other.D) {
                return false;
            }
            if (this.F != other.F) {
                return false;
            }
            if (this.J != other.J) {
                return false;
            }
            if (this.L != other.L) {
                return false;
            }
            if (this.Q != other.Q) {
                return false;
            }
            if (this.S != other.S) {
                return false;
            }
            if (this.W != other.W) {
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
        hash = 47 * hash + this.J;
        hash = 47 * hash + this.W;
        hash = 47 * hash + this.F;
        hash = 47 * hash + this.L;
        hash = 47 * hash + this.Q;
        hash = 47 * hash + this.S;
        hash = 47 * hash + this.D;
        return hash;
    }
}
