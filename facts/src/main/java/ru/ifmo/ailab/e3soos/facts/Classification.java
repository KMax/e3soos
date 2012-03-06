package ru.ifmo.ailab.e3soos.facts;

public class Classification {

    private int J;
    private int W;
    private int F;
    private int L;
    private int Q;
    private int S;
    private int D;

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
}
