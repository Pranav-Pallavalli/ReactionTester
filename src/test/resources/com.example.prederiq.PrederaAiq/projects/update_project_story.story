Scenario: To Test the put api request status codes along with its working
Given The URI <URI> to which the update req is to be made
When The put req is made where the key <key> and value <value> are modified and passed in as the body
Then status code is <status> and the the parameter <param> is updated as expected <expected>
Examples:
|URI|key|value|status|param|expected|
|https://sandbox.predera.com/aiq/api/projects|name|Nikhil|200|name|Nikhil|
|https://sandbox.predera.com/aiq/api/projects|description|Test|200|description|Test|
|https://sandbox.predera.com/aiq/api/projects|users|pranav|200|users|pranav|
|https://sandbox.predera.com/aiq/api/projects|created_by|virat kohli|200|created_by|virat kohli|

