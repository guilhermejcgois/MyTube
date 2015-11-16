package br.ufscar.sor.dcomp.sd.mytube.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.el.MethodNotFoundException;

/**
 *
 * @author USER
 */
public class AmazonUtilities {

	private final AWSCredentials credentials;
	private Map<String, AmazonService> services;

	public AmazonUtilities(AWSCredentials credentials) {
		this.credentials = credentials;
		services = new HashMap<String, AmazonService>();
	}

	public void addService(AmazonService service) {
		service.setCredentials(credentials);
		services.put(service.getName(), service);
	}

	public Object call(AmazonServiceEnum service, String method, Object... arguments) throws Exception {
		if (service.equals(AmazonServiceEnum.DYNAMODB)) {
			DynamoDBUtilities ddb = (DynamoDBUtilities) services.get(service);
			if (method.equals("getKeyVideo")) {
				return ddb.getKeyVideo((String) arguments[0]);
			} else if (method.equals("list")) {
				return ddb.list();
			} else if (method.equals("save")) {
				return ddb.save((PrimaryKey) arguments[0], (Map<String, Object>) arguments[1]);
			}
		} else if (service.equals(AmazonServiceEnum.S3)) {
			S3Utilities s3 = (S3Utilities) services.get(service);
			if (method.equals("download")) {
				return s3.download((String) arguments[0]);
			} else if (method.equals("upload")) {
				return s3.upload((String) arguments[0], (File) arguments[1]);
			}
		} else {
			throw new Exception("The utility's class for ["
					+ service + "] doesn't exists.");
		}

		throw new Exception("The utility's class for ["
				+ service + "] doesn't have a method called ["
				+ method + "].");
	}

}
