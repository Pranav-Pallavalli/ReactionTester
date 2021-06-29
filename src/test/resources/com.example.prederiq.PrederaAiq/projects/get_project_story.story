Scenario: Testing status, application type and body of a valid/invalid api call with an existent/non project id.
Given the <Url> to test
When Based on <authorisation> GET request is made to it
Then status code is <status>

Examples:
|Url|authorisation|status|
|https://sandbox.predera.com/aiq/api/projects/a-443018111|true|200|
|https://sandbox.predera.com/aiq/api/projects/a-443018111|false|401|
|https://sandbox.predera.com/aiq/api/projects/a-44301814|true|404|
|https://sandbox.predera.com/aiq/api/projects/a-44301814|false|401|
|https://sandbox.predera.com/aiq/api/projects/summary|true|200|
|https://sandbox.predera.com/aiq/api/projects/summary|false|401|

Scenario:Testing the application type and output for api request with 443018111 id
Given The valid authorized <URL>
When The request is made
Then <applicationType> is json and body is as in documentation which is <content>

Examples:
|URL|applicationType|content|
|https://sandbox.predera.com/aiq/api/projects/summary|content-type: application/json|{"owner":"ppallavalli@umass.edu","summary":{"failing_models":[],"languages":{},"project_id":"a-443018111","environments":{},"libraries":{"KERAS":1,"SKLEARN":1},"no_of_experiments":2,"no_of_models":0,"last_activity_date_experiments":"2021-06-09T17:38:08.988Z","last_activity_date_models":""},"name":"churn-juyma","description":"Customer Churn Example","last_modified_date":"2021-06-09T17:38:06.048Z","id":"a-443018111","created_date":"2021-06-09T17:38:06.048Z","last_modified_by":"ppallavalli@umass.edu","created_by":"ppallavalli@umass.edu","users":["ppallavalli@umass.edu"]}|
|https://sandbox.predera.com/aiq/api/projects/a-443018111|content-type: application/json|{"owner":"ppallavalli@umass.edu","name":"churn-juyma","description":"Customer Churn Example","last_modified_date":"2021-06-09T17:38:06.048Z","id":"a-443018111","created_date":"2021-06-09T17:38:06.048Z","last_modified_by":"ppallavalli@umass.edu","created_by":"ppallavalli@umass.edu","users":["ppallavalli@umass.edu"]}|