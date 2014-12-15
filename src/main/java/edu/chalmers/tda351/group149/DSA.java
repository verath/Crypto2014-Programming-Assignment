package edu.chalmers.tda351.group149;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Peter on 2014-12-14.
 */
public class DSA {

    private final DSADomainParameter domainParameter;


    public DSA(DSADomainParameter domainParameter) {
        this.domainParameter = domainParameter;
    }

    public DSAKeyPair generateKey() {
        SecureRandom secureRandom = new SecureRandom();

        BigInteger q = domainParameter.getQ();
        BigInteger g = domainParameter.getG();
        BigInteger p = domainParameter.getP();

        BigInteger x;
        do {
            x = new BigInteger(160, secureRandom);
            // Keep randomising until 0 < x < q
        } while (!(BigInteger.ZERO.compareTo(x) < 0 && x.compareTo(q) < 0));


        //  y = g^x mod p
        BigInteger y = g.modPow(x, p);
        return new DSAKeyPair(x, y);
    }
}
