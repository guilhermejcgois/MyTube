package br.ufscar.sor.dcomp.sd.mytube.util;

import com.amazonaws.auth.AWSCredentials;

/**
 *
 * @author USER
 */
public abstract class AmazonService {
	
	private final AWSCredentials credentials;

	public AmazonService(AWSCredentials credentials) {
		this.credentials = credentials;
	}

	public AWSCredentials getCredentials() {
		return credentials;
	}
	
	public abstract String getName();
	
}
