Scenario: To Test if the delete request to the api works properly
Given the URI <URI> to which the delete request is to be made
When Delete request is made based on whether authorization is <autho>
Then Status code returned will be <status>
Examples:
|URI|autho|status|
|https://sandbox.predera.com/aiq/api/projects/a-309310695|true|200|
|https://sandbox.predera.com/aiq/api/projects/a-941079171|false|401|
|https://sandbox.predera.com/aiq/api/projects/a-941079171|true|200|
|https://sandbox.predera.com/aiq/api/projects/a-309310695|true|404|
|https://sandbox.predera.com/aiq/api/projects/a-941079171|true|404|

