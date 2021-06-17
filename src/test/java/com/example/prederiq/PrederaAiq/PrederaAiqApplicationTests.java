package com.example.prederiq.PrederaAiq;


import com.github.tomakehurst.wiremock.WireMockServer;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
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
class PrederaAiqApplicationTests {
	String contentType;
	String uri = "https://sandbox.predera.com/aiq/api/projects";
	WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort().dynamicHttpsPort());
	CloseableHttpClient httpClient = HttpClients.createDefault();
	String auth = "Bearer " + "eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiJkNDQ2ZTdlNy02MjM5LTRiYWMtYWE0Zi00Y2JjNWQzNDk0YWUiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyMzg2NTQzOSwiZXhwIjoxNjIzODY5MDM5LCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjM4NjU0MzksImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.pcp4KY0HzcAtWIgFfoX5sRwJccQ4GizlbBqoh5GuaoRMkvPrzBtLRf1AwC2tsL8cFDni6whxhClSgW_w1cDQZUUUHQ82svDxiSBLLft1ZAg9VVOlJ1AkKbaZDcoA-4wVAZBdzmmuCiwhNerP9Ask2DiP0slAADwzNMfhlhvlqcqdWbZyEreHMuUkVkGVdSUDK933TRKkKP-x62PTsize6oi-mApmeZY3Qr5AcGHHW3frZE-XuYlLaJzDZH5yJv7qA7pkQ5c05LPlZdWrwTelEdx8GLRKRs-fFnwIquOLkWceqSyYuz3gFalXOG3xfZtVIozNfVfocZzXN54ul7_B-g";
	int status = 0;
	JSONArray array;
	HttpUriRequest httpUriRequest;
	@Test
	@Given("An authorised URL")
	void test(){
		httpUriRequest = RequestBuilder.get().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION,auth).build();
	}
	@When("A GET req is made")
	void testGet() throws IOException {
		HttpResponse response =httpClient.execute(httpUriRequest);
		status = response.getStatusLine().getStatusCode();
	}
	@Then("200 status code is returned")
	void test200() {
		assertEquals(200,status);
	}

	@Test
	@Given("Unauthorised link")
	void test2() {
		httpUriRequest = RequestBuilder.get().setUri(uri).build();
	}
	@When("GET req is made")
	void get2() throws IOException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		status = response.getStatusLine().getStatusCode();
	}
	@Then("401 status code is returned")
	void statusTest2(){
		assertEquals(401,status);
	}
	@Test
	@Given("An authorised URL.")
	void test3(){
		httpUriRequest = RequestBuilder.get().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION,auth).build();
	}
	@When("A GET req is made to it")
	void getTest3() throws IOException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		contentType = String.valueOf(response.getEntity().getContentType());
	}
	@Then("application type is json")
	void content(){
		assertEquals("application/json",contentType);
	}
	@Test
	@Given("An authorised URL.")
	void test4(){
		httpUriRequest = RequestBuilder.get().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION,auth).build();
	}
	@When("A GET req is made to it")
	void testGet4() throws IOException, ParseException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		HttpEntity entity = response.getEntity();
		Scanner scanner = new Scanner(entity.getContent());
		String content = "";
		while(scanner.hasNext()){
			content += scanner.nextLine();
		}
		scanner.close();
		JSONParser parser = new JSONParser();
		array = (JSONArray) parser.parse(content);
	}
	@Then("output same as that in documentation")
	void content2(){
		assertEquals("[{\"owner\":\"ppallavalli@umass.edu\",\"name\":\"churn-juyma\",\"description\":\"Customer Churn Example\",\"last_modified_date\":\"2021-06-09T17:38:06.048Z\",\"id\":\"a-443018111\",\"created_date\":\"2021-06-09T17:38:06.048Z\",\"last_modified_by\":\"ppallavalli@umass.edu\",\"created_by\":\"ppallavalli@umass.edu\",\"users\":[\"ppallavalli@umass.edu\"]}]",array.toString());
	}
}
