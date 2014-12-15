package edu.chalmers.tda351.group149;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Peter on 2014-12-14.
 */
public class DSA {

    private final DSADomainParameter domainParameter;
    
    private static final int L = 1024;
    private static final int N = 160;


    public DSA(DSADomainParameter domainParameter) {
        this.domainParameter = domainParameter;
    }

    public DSAKeyPair generateKey() {
        BigInteger q = domainParameter.getQ();
        BigInteger g = domainParameter.getG();
        BigInteger p = domainParameter.getP();

        BigInteger x = generatePsuedoRandomInteger(BigInteger.ZERO, q);

        //  y = g^x mod p
        BigInteger y = g.modPow(x, p);
        return new DSAKeyPair(x, y);
    }
    
    public DSASignaturePair sign(DSAKeyPair keyPair, BigInteger digest) {
    	
        BigInteger q = domainParameter.getQ();
        BigInteger g = domainParameter.getG();
        BigInteger p = domainParameter.getP();
        BigInteger x = keyPair.getX();
        BigInteger k = generatePsuedoRandomInteger(BigInteger.ZERO, q);
    	
        // r = (g^k mod p) mod q
        BigInteger r = (g.modPow(k, p)).mod(q);
        BigInteger z = digest;
        
        BigInteger temp = z.add(x.multiply(r));
        // s = ((k^-1) * (z+x*r)) mod q
        BigInteger s = k.modInverse(q).multiply(temp).mod(q);
        
        return new DSASignaturePair(r, s);
    }
    
    public boolean verify(BigInteger y, BigInteger digest, DSASignaturePair sigPair) {
    	
    	BigInteger q = domainParameter.getQ();
    	BigInteger g = domainParameter.getG();
    	BigInteger p = domainParameter.getP();
      
    	BigInteger r = sigPair.getR();
    	BigInteger s = sigPair.getS();
    	
    	if(BigInteger.ZERO.compareTo(r) >= 0 && r.compareTo(q) >= 0) {
    		return false;
    	}
    	
    	if(BigInteger.ZERO.compareTo(s) >= 0 && s.compareTo(q) >= 0) {
    		return false;
    	}
    	
    	// w = s^-1 mod q
    	BigInteger w = s.modInverse(q);
    	BigInteger z = digest;
    	
    	// u1 = z*w mod p
    	BigInteger u1 = z.multiply(w).mod(q);
    	// u2 = r*w mod q
    	BigInteger u2 = r.multiply(w).mod(q);
    	// v = (((g^u1 * y^u2) mod p) mod q)
    	BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);
    	
    	return v.equals(r);
    }
    
    private BigInteger generatePsuedoRandomInteger(BigInteger lower, BigInteger upper) {
        SecureRandom secureRandom = new SecureRandom();
    	
        BigInteger random;
    	do {
            random = new BigInteger(N, secureRandom);
            // Keep randomizing until lower < random < upper
        } while (!(lower.compareTo(random) < 0 && random.compareTo(upper) < 0));
    	
    	return random;
    }
}
