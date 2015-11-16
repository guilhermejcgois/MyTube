package br.ufscar.sor.dcomp.sd.mytube.util;

import com.amazonaws.auth.AWSCredentials;

/**
 *
 * @author USER
 */
public abstract class AmazonService {
	
	private AWSCredentials credentials;

	public AWSCredentials getCredentials() {
		return credentials;
	}
	
	protected void setCredentials(AWSCredentials credentials) {
		this.credentials = credentials;
	}
	
	public abstract String getName();
	
}
