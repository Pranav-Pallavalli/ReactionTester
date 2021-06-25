package com.example.prederiq.PrederaAiq;


import com.github.tomakehurst.wiremock.WireMockServer;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PrederaAiqApplicationTests {
	String uri;
	CloseableHttpClient httpClient = HttpClients.createDefault();
	String auth = "Bearer " + "eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiJlY2JhZDY5OC00ZmVhLTQ5NWYtODlkMS1lY2IzOGEzYzk5MjQiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyNDU3Nzg0OCwiZXhwIjoxNjI0NTgxNDQ4LCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjQ1Nzc4NDgsImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.AhBtL32ETAKsvw2PdxAP-LcPa5PtLS8fwP3HYUhMMs8LNdqD41V7pwbpTfzKaxaYMVAKRDKS4KsvH08OkmnflUN8jyODmIh0RRdRFkIPBvhq7DsWmYD61hjOzuS3oGU3x1BqukrneYUGaSS3F30RoRhBaNk7skiyRvvMfoVzxJRmASMOfSw3d8gWscBXRmNp3_8K4aKCMr6K5Oxx9EdLyJ6mImmhyMGD9550rWR5I9e29Th8mtkAvI3Z6Ze5Zhlwj7RoO-KjIem98uaOOJwkEr-CJLvlk_ULiNipSQ39PM6T3dGueb1ykbqNx2wIi5EFmjZgR-ao2Bc61aib2bwrMA";
	JSONArray array;
	HttpUriRequest httpUriRequest;

	@Given("An authorised URL")
	public void test() {
		uri = "https://sandbox.predera.com/aiq/api/projects";
	}

	@When("A GET req is made")
	public void testGet() throws IOException {
		httpUriRequest = RequestBuilder.get().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION, auth).build();
	}

	@Then("200 status code is returned")
	public void test200() throws IOException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		int status = response.getStatusLine().getStatusCode();
		Assert.assertEquals(200, status);
	}

	@Then("application type is json")
	public void contentTypeTest() throws IOException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		HttpEntity entity = response.getEntity();
		String contentType = String.valueOf(entity.getContentType());
		Assert.assertEquals("content-type: application/json", contentType);
	}

	@Then("output same as that in documentation")
	public void contentTest() throws IOException, ParseException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		HttpEntity entity = response.getEntity();
		Scanner scanner = new Scanner(entity.getContent());
		String content = "";
		while (scanner.hasNext()) {
			content += scanner.nextLine();
		}
		scanner.close();
		JSONParser parser = new JSONParser();
		array = (JSONArray) parser.parse(content);
		Assert.assertEquals("[{\"owner\":\"ppallavalli@umass.edu\",\"name\":\"churn-juyma\",\"description\":\"Customer Churn Example\",\"last_modified_date\":\"2021-06-09T17:38:06.048Z\",\"id\":\"a-443018111\",\"created_date\":\"2021-06-09T17:38:06.048Z\",\"last_modified_by\":\"ppallavalli@umass.edu\",\"created_by\":\"ppallavalli@umass.edu\",\"users\":[\"ppallavalli@umass.edu\"]}]", array.toString());
	}


	@Given("Unauthorised link")
	public void test2() {
		uri = "https://sandbox.predera.com/aiq/api/projects";
	}

	@When("GET req is made")
	public void get2() throws IOException {
		httpUriRequest = RequestBuilder.get().setUri(uri).build();
	}

	@Then("401 status code is returned")
	public void statusTest2() throws IOException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		int status = response.getStatusLine().getStatusCode();
		Assert.assertEquals(401, status);
	}
}
