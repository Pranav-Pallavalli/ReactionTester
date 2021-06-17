Given: An authorised URL
When: A GET req is made
Then: 200 status code is returned

Given:Unauthorised link
When:GET req is made
Then: 401 status code is returned

Given: An authorised URL.
When: A GET req is made to it
Then: application type is json

Given: An authorised URL.
When: A GET req is made to it
Then: output same as that in documentation