Feature: Validation Place API's

  @AddPlace @Regression
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
    #  |Blagoje|ENG  |Slovacka            |
    #  |Lav    |FRA  |Pavla Ivica 1       |

  @DeletePlace @Regression
  Scenario: Verify if delete place functionality is working
    Given DeletePlace Payload
    When User calls "DeletePlaceAPI" with "post" http request
    Then the API call got success with status call 200
    And "status" in response body is "OK"



