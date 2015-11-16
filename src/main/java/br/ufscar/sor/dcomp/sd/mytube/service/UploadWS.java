package br.ufscar.sor.dcomp.sd.mytube.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author USER
 */
@WebService(serviceName = "UploadService")
public class UploadWS {

	/**
	 * This is a sample web service operation
	 */
	@WebMethod(operationName = "upload")
	public String upload(@WebParam(name = "video") String video) {
		AmazonS3 s3client = new AmazonS3Client();
		return "Hello " + video + " !";
	}
}
