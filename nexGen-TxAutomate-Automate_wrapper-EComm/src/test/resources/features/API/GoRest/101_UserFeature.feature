Feature: API Tests

@APItests
Scenario: Retrieve information from the endpoint using Excel stored ID
  Given The API endpoint for GET has baseURI "https://gorest.co.in" and basePath "/public/v2/users/{id}"
  And I read the user ID from Excel file
  When I send a GET request to the endpoint
  Then I should receive a response with status code 200
  And the JSON path "id" should have value "<stored_id>"


@APItests
Scenario Outline: Create a new user with a unique email
    Given The API endpoint for POST has baseURI "https://gorest.co.in" and basePath "/public/v2/users"
    When I generate a unique email and store it as "generated_email"
    And I send a POST request with access token in the header and "<name>" "generated_email" "<gender>" "<status>" to the endpoint
    Then I should receive a response with status code 201
    And the JSON path "name" should have value "<name>"
    And the JSON path "email" should have value "generated_email"
    And the JSON path "gender" should have value "<gender>"
    And the JSON path "status" should have value "<status>"

  Examples:
    | name   | gender | status  |
    | Ankita | female | active  |


@APItests
Scenario Outline: Update an existing user with a unique email
    Given The API endpoint for PUT has BaseURI "https://gorest.co.in", BasePath "/public/v2/users/{id}"
    When I generate a unique email and store it as "generated_email"
    And I send a PUT request with access token in the header and "<name>" "generated_email" "<gender>" "<status>" to the endpoint
    Then I should receive a response with status code 200
    And the JSON path "name" should have value "<name>"
    And the JSON path "email" should have value "generated_email"
    And the JSON path "gender" should have value "<gender>"
    And the JSON path "status" should have value "<status>"

  Examples:
    | name   | gender | status   |
    | Ankita | female | inactive |


@APItests 
Scenario: DELETE data from the endpoint
  Given The API endpoint for DELETE has baseURI "https://gorest.co.in" and basePath "/public/v2/users/{id}"
  When I send a DELETE request with access token in the header to the endpoint
  Then I should receive a response with status code 204


@APItests 
Scenario Outline: Create new post to the endpoint
  Given The API endpoint for NEW POST has baseURI "https://gorest.co.in" and basePath "/public/v2/users/{id}/posts"
  When I send a CREATE POST request with access token in the header and "<title>" "<body>" to the endpoint
  Then I should receive a response with status code 201
  And the JSON path "title" should have value "<title>"
  And the JSON path "body" should have value "<body>"

  Examples:
    | title     | body                        |
    | Himachal  | Himachal is a great state.  |


@APItests 
Scenario Outline: Create new todo to the endpoint
  Given The API endpoint for NEW TODO has baseURI "https://gorest.co.in" and basePath "/public/v2/users/{id}/todos"
  When I send a CREATE TODO request with access token in the header and "<title>" "<status>" to the endpoint
  Then I should receive a response with status code 201
  And the JSON path "title" should have value "<title>"
  And the JSON path "status" should have value "<status>"

  Examples:
    | title | status    |
    | Test  | completed |