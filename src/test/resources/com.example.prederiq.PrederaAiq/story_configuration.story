Scenario:Testing the status code,body and body type of the GET API response
Given An authorised URL
When A GET req is made
Then 200 status code is returned
And application type is json

Scenario:Testing an unauthorised URL
Given Unauthorised link
When GET req is made
Then 401 status code is returned

