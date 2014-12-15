package edu.chalmers.tda351.group149;

import java.math.BigInteger;

/**
 * Created by Peter on 2014-12-15.
 */
public class DSAKeyPair {
    /**
     * DSA private key
     */
    private BigInteger x;

    /**
     * DSA public Key
     */
    private BigInteger y;

    /**
     * @param x the DSA private key. Must not be null.
     * @param y the DSA public key. Must not be null.
     */
    public DSAKeyPair(BigInteger x, BigInteger y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("x and y must not be null");
        }
        this.x = x;
        this.y = y;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }
}
