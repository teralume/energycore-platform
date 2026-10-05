@reporting
Feature: Reporting and Energy Goals
  Background:
    Given the RESTful API is available at base path "/api/v1"
    And the client is authenticated as "carlos.mendoza@example.com"

  Scenario: Retrieving the platform reporting summary
    When the client sends a GET request to "/reporting/platform/summary"
    Then the response status code is 200
    And the response body contains a non-empty "userId"

  Scenario: Creating an energy saving goal
    When the client sends a POST request to "/reports/energy-goals" with body:
      """
{"title":"Reduce 10% this month","targetKilowattHours":120.5,"deadline":"2030-12-31"}
"""
    Then the response status code is 201
    And the response body contains "title" equal to "Reduce 10% this month"

  Scenario: Listing energy goals
    Given the client sends a POST request to "/reports/energy-goals" with body:
      """
{"title":"Active goal","targetKilowattHours":100,"deadline":"2030-12-31"}
"""
    When the client sends a GET request to "/reports/energy-goals"
    Then the response status code is 200
    And the response body is a list with 1 item

  Scenario: Updating an energy goal target
    Given the client sends a POST request to "/reports/energy-goals" with body:
      """
{"title":"Editable goal","targetKilowattHours":120,"deadline":"2030-12-31"}
"""
    When the client sends a PATCH request to "/reports/energy-goals/{last}" with body:
      """
{"targetKilowattHours":99.9}
"""
    Then the response status code is 200
    And the response body contains "targetKilowattHours" equal to "99.9"

  Scenario: Deleting a stored report
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Report Device","type":"SMART_PLUG","powerWatts":90}
      """
    And the last created resource is remembered as "device"
    And the client sends a POST request to "/energy-readings" with body:
      """
{"deviceId":{device},"deviceName":"Report Device","watts":90}
      """
    And the response status code is 200
    And the client sends a POST request to "/reports" with body:
      """
{"startDate":"2026-10-01","endDate":"2026-10-31"}
      """
    And the response status code is 201
    And the client sends a GET request to "/reports"
    And the first response item is remembered as "report"
    When the client sends a DELETE request to "/reports/{report}"
    Then the response status code is 204

  Scenario: Exporting energy readings as CSV
    Given the client sends a POST request to "/energy-readings" with body:
      """
{"deviceId":201,"deviceName":"CSV Device","watts":75}
"""
    When the client sends a GET request to "/energy-readings/export" accepting "text/csv"
    Then the response status code is 200
    And the response "Content-Type" header starts with "text/csv"
    And the response body starts with a CSV header row
