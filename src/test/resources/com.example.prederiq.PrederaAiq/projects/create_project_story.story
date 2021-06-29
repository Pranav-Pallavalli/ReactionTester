Scenario:Testing status code based on the body parameters
Given the URL <URI> to which the post request is to be made
When post request is made with or without <authorization> with body params <name> <description> and <owners>
Then Status code should be <status>
Examples:
|URI|authorization|name|description|owners|status|
|https://sandbox.predera.com/aiq/api/projects|true|project-001|Test1|null|201|
|https://sandbox.predera.com/aiq/api/projects|false|project-002|Test2|null|401|
|https://sandbox.predera.com/aiq/api/projects|true|project-002|Test3|pranav|201|
|https://sandbox.predera.com/aiq/api/projects|true|project 004|Test4|pranav|400|
|https://sandbox.predera.com/aiq/api/projects|true|project-003_9.1111|Test5|null|400|
|https://sandbox.predera.com/aiq/api/projects|true|project-004|""|null|400|
|https://sandbox.predera.com/aiq/api/projects|true|""|""|null|400|
|https://sandbox.predera.com/aiq/api/projects|true|project-002|Test6|pranav|400|
|https://sandbox.predera.com/aiq/api/projects|true|project-002|Test3|pranav|400|

Scenario: Testing the content and contentType of a valid post call
Given The URI
When A valid post req is made to it
Then the scenario must be true

