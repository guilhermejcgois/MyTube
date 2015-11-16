package br.ufscar.sor.dcomp.sd.mytube.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.el.MethodNotFoundException;

/**
 *
 * @author USER
 */
public class AmazonUtilities {

	Map<String, AmazonService> services;

	public AmazonUtilities() {
		services = new HashMap<String, AmazonService>();
	}

	public void addService(AmazonService service) {
		services.put(service.getName(), service);
	}

	public Object call(String service, String method, Object... arguments) throws Exception {
		if (service.equals(AmazonServiceEnum.S3)) {
			S3Utilities s3 = ((S3Utilities) services.get(service));
			if (method.equals("download")) {
				return s3.download((String) arguments[0]);
			} else if (method.equals("upload")) {
				return s3.upload((String) arguments[0], (File) arguments[1]);
			} else {
				throw new Exception("The utility's class for ["
						+ service + "] doesn't have a method called ["
						+ method + "].");
			}
		} else {
			throw new Exception("The utility's class for ["
					+ service + "] doesn't exists.");
		}
	}

}
