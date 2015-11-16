package br.ufscar.sor.dcomp.sd.mytube.util;

/**
 *
 * @author USER
 */
public enum AmazonServiceEnum {
	
	DYNAMODB("DynamoDB"),
	S3("S3");

	private final String name;
	private AmazonServiceEnum(String name) {
		this.name = name;
	}
}
