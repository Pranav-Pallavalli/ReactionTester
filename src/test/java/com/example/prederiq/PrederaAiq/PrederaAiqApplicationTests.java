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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PrederaAiqApplicationTests {
	private static ThreadLocal<HttpEntity> entityThreadLocal= ThreadLocal.withInitial(() -> null);
	String uri;
	CloseableHttpClient httpClient = HttpClients.createDefault();
	String auth = "Bearer " + "eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiJkZWEzYWQ2YS1iNDQ1LTQ1MzYtOThhMy1mYmU1MmEzMjEyYTIiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyNDYzMzY1MywiZXhwIjoxNjI0NjM3MjUzLCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjQ2MzM2NTMsImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.GD6UCKRVvoj6N7rsG6wB6vi6v6kgGeDOBqD1ARo_-xOYxKocNZ7EV6ZOVYFc6s0VFE4mkETCp41Gj_YJJD0JKYMwqkoAnYQQi9rzC9mb2XDrRKEMaNTkXYXyNzIAvD2Q6MhMd5lOWvgYoV5j7itbMQXntzRKHC_jx4O83PgLaqP9YoNevgIW3I10I-dnX50HzQrWsi4ChqJfM9OCcUs0skW94waMZNJSciObLfE7kxg76mgQwycTHbRrgwVrgHIuE1JbuUfztBmetRJrPpNgkNg_TzmbGoCBEaGCvN6taYeCd0ityaYPT-lxsPcjB0c7twjXSevy7qp48FJ4U7gqEA";
	JSONArray array;
	JSONObject object = new JSONObject();
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
		entityThreadLocal.set(response.getEntity());
		Assert.assertEquals(200, status);
	}

	@Then("application type is json")
	public void contentTypeTest() throws IOException, ParseException {
		HttpEntity entity = entityThreadLocal.get();
		String contentType = String.valueOf(entity.getContentType());
		Scanner scanner = new Scanner(entity.getContent());
		String content = "";
		while (scanner.hasNext()) {
			content += scanner.nextLine();
		}
		scanner.close();
		JSONParser parser = new JSONParser();
		array = (JSONArray) parser.parse(content);
		Assert.assertEquals("[{\"owner\":\"ppallavalli@umass.edu\",\"name\":\"churn-juyma\",\"description\":\"Customer Churn Example\",\"last_modified_date\":\"2021-06-09T17:38:06.048Z\",\"id\":\"a-443018111\",\"created_date\":\"2021-06-09T17:38:06.048Z\",\"last_modified_by\":\"ppallavalli@umass.edu\",\"created_by\":\"ppallavalli@umass.edu\",\"users\":[\"ppallavalli@umass.edu\"]}]", array.toString());
		Assert.assertEquals("content-type: application/json", contentType);
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

	//Tests for GET ID
	//And Get Summary;
	String auth2 = "Bearer "+ "eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiJkZTU1ZGIzMC1hZTViLTRkYmItYTgwYS0xNzZhYTNhOTNmNWIiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyNTAwNjkxNiwiZXhwIjoxNjI1MDEwNTE2LCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjUwMDY5MTYsImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.CJGB84yFWWAQaTDf8-9xMsYc0dnifeAZcXGvTzbGZ5vaX8It3KyY0z9-ov-eJTUUbex6wp3SX0467QeDtjo8e6KbLKiMq_dPh_JysS9cJcUvexO8PDAwd_9n2WMxpSIJbWUkFHmPm3xil_bj2EulV6xMwAoLMS6Zj9k0pXBeAz0i_fX_VAcwkk7vkNQR4oMHpj-ckRflIP_m6cWGMq68KAPS-_yM2D8Och67Rgn0ewOAgCo4hbdBHvbFXAgSIJrjpAgBB5xUjnti-mAuonRdHM_tXG1B2Ib3HYFmXnnSfrn1x_Ax73odkT1DTMuulTc9ufsL70PeRx5vdJaULXEgYg";
	@Given("the <Url> to test")
	public void testUrl(@Named("Url")String Url){
		uri = Url;
	}
	@When("Based on <authorisation> GET request is made to it")
	public void getReq(@Named("authorisation")boolean autho){
		if(autho) {
			httpUriRequest = RequestBuilder.get().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION, auth2).build();
		}
		else {
			httpUriRequest = RequestBuilder.get().setUri(uri).build();
		}
	}
	@Then("status code is <status>")
	public void sc(@Named("status") int status) throws IOException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		int status1 = response.getStatusLine().getStatusCode();
		Assert.assertEquals(status,status1);
		httpUriRequest.abort();
	}
	//Tests for content type and content of a valid call
	@Given("The valid authorized <URL>")
	public void testUrl2(@Named("URL")String URL){
		uri = URL;
	}
	@When("The request is made")
	public void getReq2(){

			httpUriRequest = RequestBuilder.get().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION, auth2).build();
	}
	@Then("<applicationType> is json and body is as in documentation which is <content>")
	public void sc2(@Named("applicationType")String type,@Named("content") String body) throws ParseException, IOException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		HttpEntity entity = response.getEntity();
		String contentType = String.valueOf(entity.getContentType());
		Assert.assertEquals(type,contentType);
		Scanner scanner = new Scanner(entity.getContent());
		String content = "";
		while (scanner.hasNext()) {
			content += scanner.nextLine();
		}
		scanner.close();
		JSONParser parser = new JSONParser();
		if(!uri.equals("https://sandbox.predera.com/aiq/api/projects/summary")) {
			object = (JSONObject) parser.parse(content);
		}
		else {
			array = (JSONArray) parser.parse(content);
		}
		if(!uri.equals("https://sandbox.predera.com/aiq/api/projects/summary")) {
			Assert.assertEquals(body,object.toString() );
		}
		else {
			System.out.println(array.toString());
			Assert.assertEquals(body,array.get(0).toString());
		}
		httpUriRequest.abort();
	}

	//args/count API content and content type tests
	String auth6 = "Bearer "+"eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiJkZTU1ZGIzMC1hZTViLTRkYmItYTgwYS0xNzZhYTNhOTNmNWIiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyNTAwNjkxNiwiZXhwIjoxNjI1MDEwNTE2LCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjUwMDY5MTYsImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.CJGB84yFWWAQaTDf8-9xMsYc0dnifeAZcXGvTzbGZ5vaX8It3KyY0z9-ov-eJTUUbex6wp3SX0467QeDtjo8e6KbLKiMq_dPh_JysS9cJcUvexO8PDAwd_9n2WMxpSIJbWUkFHmPm3xil_bj2EulV6xMwAoLMS6Zj9k0pXBeAz0i_fX_VAcwkk7vkNQR4oMHpj-ckRflIP_m6cWGMq68KAPS-_yM2D8Och67Rgn0ewOAgCo4hbdBHvbFXAgSIJrjpAgBB5xUjnti-mAuonRdHM_tXG1B2Ib3HYFmXnnSfrn1x_Ax73odkT1DTMuulTc9ufsL70PeRx5vdJaULXEgYg";
	int count = 0;

	@Given("The arg/count api URI <URI>")
	public void test9(@Named("URI")String URI){
		uri = URI;
	}
	@When("It is called using get req")
	public void getget() throws IOException {
		if (count==0){
			count++;
			httpUriRequest = RequestBuilder.get().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION,auth6).build();
		}
		else {
			JSONObject temp = new JSONObject();
			JSONArray tempArr = new JSONArray();
			String projectName = "project-"+String.valueOf(0)+String.valueOf(count);
			temp.put("name",projectName);
			temp.put("description","Test");
			temp.put("users",tempArr);
			System.out.println("entered");
			httpUriRequest = RequestBuilder.post("https://sandbox.predera.com/aiq/api/projects").setHeader(HttpHeaders.CONTENT_TYPE,"application/json").addHeader(HttpHeaders.AUTHORIZATION,auth4).setEntity(new StringEntity(temp.toString())).build();
			httpClient.execute(httpUriRequest);
			httpUriRequest.abort();
			count++;
			httpUriRequest = RequestBuilder.get().setUri(uri).addHeader(HttpHeaders.AUTHORIZATION,auth6).build();
		}
	}
	@Then("application type is <type> and body is <contentt> as in documentation")
	public void cont(@Named("type")String type,@Named("contentt")String content) throws IOException, ParseException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		String contentType = String.valueOf(response.getEntity().getContentType());
		String str = "";
		Scanner scanner = new Scanner(response.getEntity().getContent());
		while (scanner.hasNext()){
			str += scanner.nextLine();
		}
		scanner.close();
		JSONParser parser = new JSONParser();
		object = (JSONObject) parser.parse(str);
		Assert.assertEquals(content,object.toString());
		httpUriRequest.abort();
	}
	//Testing the POST API

	String auth4 = "Bearer " + "eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiJkZTU1ZGIzMC1hZTViLTRkYmItYTgwYS0xNzZhYTNhOTNmNWIiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyNTAwNjkxNiwiZXhwIjoxNjI1MDEwNTE2LCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjUwMDY5MTYsImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.CJGB84yFWWAQaTDf8-9xMsYc0dnifeAZcXGvTzbGZ5vaX8It3KyY0z9-ov-eJTUUbex6wp3SX0467QeDtjo8e6KbLKiMq_dPh_JysS9cJcUvexO8PDAwd_9n2WMxpSIJbWUkFHmPm3xil_bj2EulV6xMwAoLMS6Zj9k0pXBeAz0i_fX_VAcwkk7vkNQR4oMHpj-ckRflIP_m6cWGMq68KAPS-_yM2D8Och67Rgn0ewOAgCo4hbdBHvbFXAgSIJrjpAgBB5xUjnti-mAuonRdHM_tXG1B2Ib3HYFmXnnSfrn1x_Ax73odkT1DTMuulTc9ufsL70PeRx5vdJaULXEgYg";
	JSONArray arr = new JSONArray();
	JSONObject obj = new JSONObject();
	@Given("the URL <URI> to which the post request is to be made")
	public void testUrl3(@Named("URI") String URI){
		uri = URI;
	}

	@When("post request is made with or without <authorization> with body params <name> <description> and <owners>")
	public void post(@Named("authorization")boolean authorize,@Named("name")String name,@Named("description")String desc,@Named("owners")String owners) throws UnsupportedEncodingException {
	if(authorize){
		if(!owners.equals(null)){
			arr.add(owners);
		}
			obj.put("name", name);
			obj.put("description", desc);
			obj.put("users", arr);
		httpUriRequest = RequestBuilder.post(uri).setHeader(HttpHeaders.CONTENT_TYPE,"application/json").addHeader(HttpHeaders.AUTHORIZATION,auth4).setEntity(new StringEntity(obj.toString())).build();
	}
	else {
		if(!owners.equals(null)){
			arr.add(owners);
		}
		obj.put("name",name);
		obj.put("description",desc);
		obj.put("users",arr);
		httpUriRequest = RequestBuilder.post(uri).setHeader(HttpHeaders.CONTENT_TYPE,"application/json").setEntity(new StringEntity(obj.toString())).build();
	}
	}

	@Then("Status code should be <status>")
	public void sc3(@Named("status")int status) throws IOException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		int status1 = response.getStatusLine().getStatusCode();
		Assert.assertEquals(status,status1);
		obj.clear();
		arr.clear();
		httpUriRequest.abort();
	}
	@Given("The URI")
	public void test4(){
		uri = "https://sandbox.predera.com/aiq/api/projects";
	}
	@When("A valid post req is made to it")
	public void post2() throws UnsupportedEncodingException {
		obj.clear();
		arr.clear();
		obj.put("name","project-020");
		obj.put("description","Sample for Test");
		obj.put("users",arr);
		httpUriRequest = RequestBuilder.post(uri).setHeader(HttpHeaders.CONTENT_TYPE,"application/json").addHeader(HttpHeaders.AUTHORIZATION,auth4).setEntity(new StringEntity(obj.toString())).build();
	}
	@Then("the scenario must be true")
	public void sc4() throws IOException, ParseException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		HttpEntity entity = response.getEntity();
		String contentType = String.valueOf(entity.getContentType());
		Assert.assertEquals("content-type: application/json",contentType);
		Scanner scanner = new Scanner(entity.getContent());
		String content = "";
		while (scanner.hasNext()) {
			content += scanner.nextLine();
		}
		scanner.close();
		JSONParser parser = new JSONParser();
		JSONObject temp = (JSONObject) parser.parse(content);
		Assert.assertEquals("{\"owner\":\"ppallavalli@umass.edu\",\"name\":\"project-020\",\"description\":\"Sample for Test\",\"last_modified_date\":\"2021-06-29T00:15:10.936Z\",\"id\":\"a-941079110\",\"created_date\":\"2021-06-29T00:15:10.936Z\",\"last_modified_by\":\"ppallavalli@umass.edu\",\"created_by\":\"ppallavalli@umass.edu\",\"users\":[]}",temp.toString());
	}
	String auth5 = "Bearer "+"eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiJiMzY0N2EyMC0zYTY2LTQ0YzYtOGY1Ny1kNTI2MGMwZjRmNDIiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyNDk4NTkzMSwiZXhwIjoxNjI0OTg5NTMxLCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjQ5ODU5MzEsImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.JBFTcgJ_k2fmSsd7EbF0P3Zs59FaPthbSeRHnwbrgu9euyVrDACS2PqxEgl6xgU3FDUP0ptLoKdYrosiJYFwMyH22J7Qm264qtwqgBU-1gA7kr0kuJzYjgwlCysFkDoZvd3RrulkqtvQQBdgT9jexC1YL0gErckPJqwdFauzDXYkZTdDUoYY_8MtcDf9Td5v9flujNC1teaG22pOYnj1MtjlOXlmD51zO1KQCuf_-3JKBaY40VuJHpgTQKD-n2Pp6ZT5KjekqGvfRbsXbDfbHd2YBSWjHoKdXPglyG4VkrqBDIqVP-YmdmoThYPz_8Tjod0zQge-oaVi_KRIY-17Tw";
	@Given("the URI <URI> to which the delete request is to be made")
	public void testUrl5(@Named("URI")String URI){
		uri = URI;
	}
	@When("Delete request is made based on whether authorization is <autho>")
	public void delete(@Named("autho")boolean autho){
		if(autho) {
			httpUriRequest = RequestBuilder.delete().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION, auth5).build();
		}
		else {
			httpUriRequest = RequestBuilder.delete().setUri(uri).build();
		}
	}
	@Then("Status code returned will be <status>")
	public void sc6(@Named("status")int status) throws IOException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		int status1 = response.getStatusLine().getStatusCode();
		Assert.assertEquals(status,status1);
		httpUriRequest.abort();
	}

	//Testing the PUT command by seeing if the parameter which is modified in the body is also modified in the response of the API
	String auth7 = "Bearer " + "eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiIxYTVmMWViOC03OGQ0LTRhMGItOWI4ZS1hOTQzOTU1NjllMmIiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyNTA5MjM5NCwiZXhwIjoxNjI1MDk1OTk0LCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjUwOTIzOTUsImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.chKdOFixoRnflkaa8Oar7I0zbUX8IriVGMm0xObyFyPhvbVqcZynL3EPgLMTLnIvpuPj47-1ivdKAO3271kqe1Km7ZoZWhm_77KWDb8-t7_Yic5I35xe32d7kalYRJNTrf6wjPmFkqYDrf60mw6N52o2k6IOEFv159sGLv_PzWFRRMqzim8btnW-3MHVeGzhLWv_bwFhZxv3srOCZHPXV44046VLALxgTXo5A3vw16mkTuTbVbx05dj9iXBkuiRZb95DPKpsEi3wqqFDO0mD-InhCyOVFwmY5PAqA_zMj2NtUnCnvPpBbeRTfN8ZKE1MMMYvoEzu-tVzQtNpcc5jaQ";
	@Given("The URI <URI> to which the update req is to be made")
	public void testUrl7(@Named("URI")String URI){
		uri = URI;
	}
	@When("The put req is made where the key <key> and value <value> are modified and passed in as the body")
	public void put(@Named("key")String key,@Named("value")String value) throws IOException, ParseException {
		httpUriRequest = RequestBuilder.get().setUri(uri).addHeader(HttpHeaders.AUTHORIZATION,auth7).build();
		HttpResponse response = httpClient.execute(httpUriRequest);
		String str = "";
		Scanner scanner = new Scanner(response.getEntity().getContent());
		while (scanner.hasNext()){
			str+=scanner.nextLine();
		}
		scanner.close();
		JSONParser parser = new JSONParser();
		JSONArray temparr1 = (JSONArray)parser.parse(str);
		JSONObject jsonObject = (JSONObject) temparr1.get(0);
		String id = jsonObject.getAsString("id");
		String name = jsonObject.getAsString("name");
		String description = jsonObject.getAsString("description");
		String owners = jsonObject.getAsString("owner");
		JSONArray jsonArray = (JSONArray)jsonObject.get("users");
		httpUriRequest.abort();
		object.clear();
		object.put("id",id);
		object.put("name",name);
		object.put("description",description);
		object.put("owner",owners);
		object.put("users",jsonArray);
		if(key.equals("users")){
			jsonArray.add(value);
			object.put("users",jsonArray);
		} else {
			object.put(key,value);
		}
		httpUriRequest = RequestBuilder.put(uri).setHeader(HttpHeaders.CONTENT_TYPE,"application/json").addHeader(HttpHeaders.AUTHORIZATION,auth7).setEntity(new StringEntity(object.toString())).build();
	}
	@Then("status code is <status> and the the parameter <param> is updated as expected <expected>")
	public void putTest(@Named("status")int status,@Named("param") String param,@Named("expected")String value) throws IOException, ParseException {
		HttpResponse response = httpClient.execute(httpUriRequest);
		Assert.assertEquals(status,response.getStatusLine().getStatusCode());
		String str = "";
		Scanner scanner = new Scanner(response.getEntity().getContent());
		while (scanner.hasNext()){
			str += scanner.nextLine();
		}
		scanner.close();
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(str);
		if(!param.equals("users")) {
			Assert.assertEquals(value, jsonObject.get(param));
		} else {
			JSONArray jsonArr2 = (JSONArray) jsonObject.get("users");
			String jsonObject2 = (String) jsonArr2.get(0);
			Assert.assertEquals(value,jsonObject2.toString());
		}
		httpUriRequest.abort();
	}
}
