package edu.chalmers.tda351.group149;


import java.math.BigInteger;

/**
 * Created by Peter on 2014-12-14.
 */
public class DSADomainParameter {

    // Page 69
    public final static int PRIME_CERTAINTY = 40;

    private final BigInteger p;
    private final BigInteger q;
    private final BigInteger g;

    /**
     * Creates a new DSA Domain Parameter triple.
     *
     * @param p The p value. Must not be null.
     * @param q The q value. Must not be null.
     * @param g The g value. Must not be null.
     */
    public DSADomainParameter(BigInteger p, BigInteger q, BigInteger g) {
        if (p == null || q == null || g == null) {
            throw new IllegalArgumentException("The values for any of p, q and g must not be null.");
        }

        this.p = p;
        this.q = q;
        this.g = g;
    }

    public DSADomainParameter(String p, String q, String g, int radix) {
        this(new BigInteger(p, radix), new BigInteger(q, radix), new BigInteger(g, radix));
    }

    public DSADomainParameter(String p, String q, String g) {
        this(p, q, g, 10);
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getG() {
        return g;
    }

    /**
     * Tests if the parameter is valid according to the following;
     * - both p and q are primes;
     * - p is a 1024 bit number and q a 160 bit number;
     * - q is a divisor of p-1;
     * - g has order q i.e. g^q mod p = 1 and g > 1.
     *
     * @return true if valid, false if in any way the parameters are not valid.
     */
    public boolean isValid() {
        if (!p.isProbablePrime(DSADomainParameter.PRIME_CERTAINTY)) {
            // p is not a prime
            return false;
        }

        if(!q.isProbablePrime(DSADomainParameter.PRIME_CERTAINTY)) {
            // q is not a prime
            return false;
        }

        if(p.bitLength() != 1024) {
            // p is not a 1024 bit number
            return false;
        }

        if(q.bitLength() != 160) {
            // q is not a 160 bit number
            return false;
        }

        // (p-1) % q != 0
        if(!p.subtract(BigInteger.ONE).mod(q).equals(BigInteger.ZERO)) {
            // q is not a divisor of p-1
            return false;
        }

        if(g.compareTo(BigInteger.ONE) <= 0) {
            // g <= 1
            return false;
        }

        // g^q mod p != 1
        if(!g.modPow(q, p).equals(BigInteger.ONE)) {
            return false;
        }

        return true;

    }
}
