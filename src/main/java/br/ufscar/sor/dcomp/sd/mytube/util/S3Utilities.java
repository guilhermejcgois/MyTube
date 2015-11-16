package br.ufscar.sor.dcomp.sd.mytube.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.File;

/**
 *
 * @author USER
 */
public class S3Utilities extends AmazonService {

	private final AmazonS3 s3Client;
	private AmazonWebServiceRequest s3Request;
	
	private String bucketName;

	public S3Utilities(AWSCredentials credentials) {
		super(credentials);

		s3Client = new AmazonS3Client(getCredentials());
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	
	public S3ObjectInputStream download(String keyName) {
		s3Request = new GetObjectRequest(bucketName, keyName);
		S3Object s3Object = s3Client.getObject((GetObjectRequest) s3Request);
		
		System.out.println("Content-type: " + s3Object.getObjectMetadata().getContentType());
		
		return s3Object.getObjectContent();
	}

	public String upload(String keyName, File file) {
		s3Request = new PutObjectRequest(bucketName, keyName, file);

		try {
			s3Client.putObject((PutObjectRequest) s3Request);
			
			return "keyname";
		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which "
					+ "means your request made it "
					+ "to Amazon S3, but was rejected with an error response"
					+ " for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which "
					+ "means the client encountered "
					+ "an internal error while trying to "
					+ "communicate with S3, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
		
		return null;
	}

	@Override
	public String getName() {
		return AmazonServiceEnum.S3.name();
	}

}
