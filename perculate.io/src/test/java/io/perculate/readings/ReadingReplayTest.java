package io.perculate.readings;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
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
import com.squareup.okhttp.Response;

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
		readings = Resources.readLines(ReadingReplayTest.class.getResource("/data.csv"), Charsets.UTF_8);
	}

	@Test
	public void insertRecordedReadings() throws Exception {
		for (int i = 0; i < 20; i++) {
			Response response = executeRequest(readings.get(i));
			assertTrue(response.isSuccessful());
			pauseForHalfOfSecond(); 
		}
	}

	private Response executeRequest(String reading) throws JsonProcessingException, IOException {
		Request request = createPostRequest(createRequest(reading));
		Response response = client.newCall(request).execute();
		response.body().close();
		return response;
	}

	private Request createPostRequest(RequestBody body) {
		return new Request.Builder().url("http://localhost:8080/readings").post(body).build();
	}

	private RequestBody createRequest(String reading) throws JsonProcessingException {
		String json = String.format("{\"weight\":%s}", reading);
		return RequestBody.create(JSON, json);
	}
	
	private static void pauseForHalfOfSecond() throws InterruptedException {
		Thread.sleep(500);
	}
}
