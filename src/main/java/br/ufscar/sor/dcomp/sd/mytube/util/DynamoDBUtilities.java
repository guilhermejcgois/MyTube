package br.ufscar.sor.dcomp.sd.mytube.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author USER
 */
public class DynamoDBUtilities extends AmazonService {

	private final AmazonDynamoDB dynamoClient;
	private final DynamoDB dynamoDB;
	private final Table table;

	public DynamoDBUtilities(AWSCredentials credentials, String tableName) {
		super(credentials);

		dynamoClient = new AmazonDynamoDBClient(getCredentials());
		dynamoClient.setEndpoint("dynamodb.sa-east-1.amazonaws.com/");

		dynamoDB = new DynamoDB(dynamoClient);

		table = dynamoDB.getTable(tableName);
	}

	public void save(PrimaryKey pk, Map<String, Object> infoMap) {
		Item item = new Item()
				.withPrimaryKey(pk)
				.withMap("info", infoMap);

		/*
		 For conditional input
		 PutItemSpec spec = new PutItemSpec()
		 .withItem(item)
		 .withConditionExpression("attribute_not_exists(#yr) and attribute_not_exists(title)")
		 .withNameMap(new NameMap()
		 .with("#yr", "year"));
		 */
		table.putItem(item);
	}
	
	public ItemCollection<ScanOutcome> list() {
		ScanSpec scanSpec = new ScanSpec()
				.withProjectionExpression("hashVideo, description");
		
		return table.scan(scanSpec);
	}
	
	public String getKeyVideo(String hashVideo) {
		HashMap<String, String> nameMap = new NameMap()
				.with("#hv", "hashVideo");
		HashMap<String, Object> valueMap = new ValueMap()
				.with("hashVideo", hashVideo);
		
		QuerySpec querySpec = new QuerySpec()
				.withFilterExpression("#hv = :hashVideo")
				.withNameMap(nameMap)
				.withValueMap(valueMap);
		
		ItemCollection<QueryOutcome> items = table.query(querySpec);
		
		Iterator<Item> iterator = items.iterator();
		
		return iterator.next().getString("bucketKey");
	}

	@Override
	public String getName() {
		return AmazonServiceEnum.DYNAMODB.name();
	}

}
