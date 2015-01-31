package io.perculate.readings;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/***
 * This posts weight readings to the endpoint every 500ms (same rate the data was collected at)
 * Obviously you need to run the web application, then run this 'test' to insert the data.
 * 
 * It might be quicker for development to load the data into a data.sql file which Spring will 
 * load automatically. I did it this way, so when the websocket is hooked up we'll be able to
 * replay the data and see the webpage respond.
 * 
 * Note: this is ignored so it won't break Jenkins
 * 
 * @author Tom Hermann
 */
@Ignore
public class ReadingReplayTest {
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static List<String> readings;
	private final OkHttpClient client = new OkHttpClient();
	
	@BeforeClass
	public static void setup() throws Exception {
		readings = Resources.readLines(ReadingReplayTest.class.getResource("/data.csv"), Charsets.ISO_8859_1);
	}

	@Test
	public void insertRecordedReadings() throws Exception {
		for (int i = 0; i < 20; i++) {
			Request request = createPostRequest(createRequest(readings.get(i)));
			client.newCall(request).execute();
			pauseForHalfOfSecond(); 
		}
	}

	private Request createPostRequest(RequestBody body) {
		return new Request.Builder().url("http://localhost:8080/perculate/readings").post(body).build();
	}

	private RequestBody createRequest(String reading) throws JsonProcessingException {
		String json = String.format("{\"weight\":%s}", reading);
		return RequestBody.create(JSON, json);
	}
	
	private static void pauseForHalfOfSecond() throws InterruptedException {
		Thread.sleep(500);
	}
}
