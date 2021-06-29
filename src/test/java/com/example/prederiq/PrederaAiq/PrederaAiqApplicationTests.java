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
	JSONObject object;
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
	String auth2 = "Bearer "+ "eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiI1ZTgzODllNC1hMmVkLTQ5NWQtOWM3MS0wY2JkYjc1YTQ3N2UiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyNDkyNjU3MiwiZXhwIjoxNjI0OTMwMTcyLCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjQ5MjY1NzIsImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.eZ97EXeyuxRTSCeQAIYJ83IU8FVY7Yt7noPHB_cT5fE6FQSJGGcwKcyFUbaYfUS43ksyVGz5xfVRrvm7dXlOrAQ1AjhlDneOsv1fddU6UEZZMeHx8G5SkpS4_KOIphC741eTOhlLruk1KCYaw7XgMVjv53FkJmItwgVRvjwoaH2xALf3XFnqaD_1Ipb4_EbiOKvx6OJ443mBjWIcyxH0ryKOJDht4ZMTVd1Z9tKu1eMcr39db3kZBf0Ou7Ls1GmgzvZcCUT8DE6csky2QR3zwUKhKCYjl-GtQTOD8hAQVnbNgnzLS7SyuFaZiESZJtGvnDNKTJ7H2r8S54fF7-Mu4A";
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
	//Testing the POST API

	String auth4 = "Bearer " + "eyJraWQiOiJndE1YKzh2bVBaNnk0NElmdllGNDZqVDlvRG5RZWxoeUg4d1JjMVwvWkdBND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMGNhZjMyYy0zY2Q0LTQyNzAtYmQ4NC1kOWI4N2Q1NGIyZjAiLCJjdXN0b206dGllciI6IlN0YW5kYXJkIFRpZXIiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl8zTVkyM3BMM0YiLCJjb2duaXRvOnVzZXJuYW1lIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1IiwiY3VzdG9tOnRlbmFudF9pZCI6IlRFTkFOVDU4ZWFjMTM4YzIyMjQ5ZjA5MTA1MDA1Mzk2MGNmMzZhIiwiZ2l2ZW5fbmFtZSI6IlByYW5hdiIsImF1ZCI6IjZibjZrNTk0cmxubXRyamZpYXMxdjQwMGhmIiwiZXZlbnRfaWQiOiJhZjVhYTIwNi1hNzI0LTQ1YjQtYTA4Mi0wNjE5MzdlNzhlOWQiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYyNDkyMjc0OCwiZXhwIjoxNjI0OTI2MzQ4LCJjdXN0b206cm9sZSI6IlRlbmFudFVzZXIiLCJpYXQiOjE2MjQ5MjI3NDgsImZhbWlseV9uYW1lIjoiUGFsbGF2YWxsaSIsImVtYWlsIjoicHBhbGxhdmFsbGlAdW1hc3MuZWR1In0.HkV9BorwAQE_OpHnPcD0kUNrPMiJd3fcIPmRZJ87ZkB9k3cujgFS73DvB9QloEvZJHYzQehwB74429KfippV6dS56geq-JdgctSX5f3QOsXq4k1zvrnaP0WaaP7_fzSe2VBp9Zwt_js0jYS6QE3F1r1fcy-JIJlpy5vknKjbxLqjvl6E12EFK2iEhxb3GnYbXnFWGjEymGi9lY_0elD_huhJ2KaIKjMuZXlt5ESHCDNloeGagrOAoO9EWjNMul38lS8KD2ZhRNoFnuNGzmj5HtRY7I-ZfOkQ2jIGr7-E0N2p1EIjuc9OMzPZrbki8Day6oIy-G66TMcQCP4HxsBOLg";
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
}
