package br.ufscar.sor.dcomp.sd.mytube.service;

import br.ufscar.sor.dcomp.sd.mytube.util.AmazonServiceEnum;
import br.ufscar.sor.dcomp.sd.mytube.util.AmazonUtilities;
import br.ufscar.sor.dcomp.sd.mytube.util.DynamoDBUtilities;
import br.ufscar.sor.dcomp.sd.mytube.util.HashUtil;
import br.ufscar.sor.dcomp.sd.mytube.util.S3Utilities;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author USER
 */
@WebService(serviceName = "UploadService")
public class MyTubeWS {

	private final AmazonUtilities amazon;
	private final String ACCESSKEY = "";
	private final String SECRETKEY = "";

	public MyTubeWS() {
		AWSCredentials credentials = new BasicAWSCredentials(ACCESSKEY, SECRETKEY);
		amazon = new AmazonUtilities(credentials);

		amazon.addService(new DynamoDBUtilities("disiect"));
		amazon.addService(new S3Utilities("disiect"));
	}

	/**
	 * This is a sample web service operation
	 *
	 * @param destination
	 * @param name
	 * @return
	 * @throws java.lang.Exception
	 */
	@WebMethod(operationName = "download")
	public String download(@WebParam(name = "destination") String destination,
			@WebParam(name = "name") String name)
			throws Exception {
		String key = (String) amazon.call(
				AmazonServiceEnum.DYNAMODB,
				"getKeyVideo",
				new Object[]{
					HashUtil.getHashFrom(name)
				});
		S3ObjectInputStream inputStream = (S3ObjectInputStream) amazon.call(
				AmazonServiceEnum.S3,
				"download",
				new Object[]{
					key
				});

		FileOutputStream outputStream = new FileOutputStream(destination + File.pathSeparator + name);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		char[] buf = null;
		while (reader.read(buf) != -1) {
			outputStream.write(new String(buf).getBytes());
		}
		outputStream.close();

		return "Hello " + destination + " !";
	}

	/**
	 * This is a sample web service operation
	 *
	 * @param source
	 * @param name
	 * @param description
	 * @return
	 * @throws java.lang.Exception
	 */
	@WebMethod(operationName = "upload")
	public String upload(@WebParam(name = "video") String source,
			@WebParam(name = "name") String name,
			@WebParam(name = "description") String description)
			throws Exception {
		File file = new File(source);
		String key = HashUtil.getHashFrom(name);
		amazon.call(AmazonServiceEnum.S3, "upload",
				new Object[]{
					key,
					file
				});

		PrimaryKey pk = new PrimaryKey("hashVideo", key);
		Map<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("name", name);
		infoMap.put("description", description);
		amazon.call(AmazonServiceEnum.DYNAMODB, "save",
				new Object[]{
					pk,
					infoMap
				});

		return "Hello " + source + " !";
	}
	
	/**
	 * This is a sample web service operation
	 *
	 * @param source
	 * @param name
	 * @param description
	 * @return
	 * @throws java.lang.Exception
	 */
	@WebMethod(operationName = "list")
	public String list() throws Exception {
		ItemCollection<ScanOutcome> items = (ItemCollection<ScanOutcome>) amazon
				.call(AmazonServiceEnum.DYNAMODB, "list", new Object[]{});

		return "Hello!";
	}
}
