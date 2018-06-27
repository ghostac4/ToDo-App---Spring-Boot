package com.todonotes.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTToken {

	private static final Logger logger = LoggerFactory.getLogger(JWTToken.class);
	private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	private static final String secretKey = "A4A724A34645343E1AAE6512EFBEC";
	private static final String SUBJECT = "verify";
	private static final String ISSUER = "todonotes";

	public static String getToken(String id,long time) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(SUBJECT).setIssuer(ISSUER)
				.signWith(signatureAlgorithm, signingKey);
		// if it has been specified, let's add the expiration
		long expMillis = nowMillis + time;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	public static String parseJWT(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(jwt)
				.getBody();
		logger.info(
				claims.getId() + " " + claims.getSubject() + " " + claims.getIssuer() + " " + claims.getExpiration());
		return claims.getId();
	}
}
