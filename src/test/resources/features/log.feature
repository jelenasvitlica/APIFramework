Feature: Validation Place API's


  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given Add place payload with "<name>" "<language>" "<address>"
    When User calls "AddPlaceAPI" with "post" http request
    Then the API call got success with status call 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_id created maps to "<name>" using "GetPLaceAPI"

    Examples:
      |name|language|address|
      |Jelena|SRB   |Zmaj Ognjena Vuka 24|
     # |Blagoje|ENG  |Slovacka            |
     # |Lav    |FRA  |Pavla Ivica 1       |


  Scenario: Verify if delete place functionality is working
    Given DeletePlace Payload
    When User calls "DeletePlaceAPI" with "post" http request
    Then the API call got success with status call 200
    And "status" in response body is "OK"

  @Smoke
  Scenario: Verify if adding comment is working
    Given Login with user "svitlicaj" and "Makija88"
    When User calls AddComment with post http request
    Then the API call got success with status call 201


  @Smoke
  Scenario: Verify if adding attachment is working
    Given Login with user "svitlicaj" and "Makija88"
    When User calls AddAttachment with post http request
    Then the API call got success with status call 200

  @Smoke
  Scenario: Verify creating issue is working
    Given Login with user "svitlicaj" and "Makija88"
    When User calls AddIssue  with post http request
    Then the API call got success with status call 201
    And Verify issueId




