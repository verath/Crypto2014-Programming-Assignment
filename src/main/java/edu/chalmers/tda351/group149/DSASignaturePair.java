package edu.chalmers.tda351.group149;

import java.math.BigInteger;

public class DSASignaturePair {

    private BigInteger r;
    private BigInteger s;

    public DSASignaturePair(BigInteger r, BigInteger s) {
        this.r = r;
        this.s = s;
    }

    public BigInteger getR() {
        return r;
    }

    public BigInteger getS() {
        return s;
    }
}
