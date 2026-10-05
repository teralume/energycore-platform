@notifications
Feature: Notifications and Alerts
  Background:
    Given the RESTful API is available at base path "/api/v1"
    And the client is authenticated as "carlos.mendoza@example.com"

  Scenario: Creating a high-consumption alert rule
    When the client sends a POST request to "/alerts/rules" with body:
      """
{"name":"High consumption","metric":"POWER","conditionType":"GREATER_THAN","threshold":500,"level":"WARNING","scopeType":"GENERAL","evaluatorType":"ACTIVE_POWER"}
"""
    Then the response status code is 201
    And the response body contains "threshold" equal to "500"

  Scenario: Evaluating rules when the threshold is exceeded
    Given the client sends a POST request to "/alerts/rules" with body:
      """
{"name":"Evaluation rule","metric":"POWER","conditionType":"GREATER_THAN","threshold":500,"level":"WARNING","scopeType":"GENERAL","evaluatorType":"ACTIVE_POWER"}
"""
    When the client sends a POST request to "/alerts/rules/evaluate" with body:
      """
{"scopeType":"GENERAL","observedValue":650}
"""
    Then the response status code is 200

  Scenario: Listing active alerts
    Given the client sends a POST request to "/alerts" with body:
      """
{"title":"Alert 1","message":"High use","level":"WARNING"}
"""
    And the client sends a POST request to "/alerts" with body:
      """
{"title":"Alert 2","message":"High use","level":"WARNING"}
"""
    When the client sends a GET request to "/alerts"
    Then the response status code is 200
    And the response body is a list with 2 items

  Scenario: Marking an alert as read
    Given the client sends a POST request to "/alerts" with body:
      """
{"title":"Unread","message":"Please review","level":"INFO"}
"""
    When the client sends a PATCH request to "/alerts/{last}/read"
    Then the response status code is 200
    And the response body contains "readStatus" equal to "true"

  Scenario: Resolving an alert
    Given the client sends a POST request to "/alerts" with body:
      """
{"title":"Resolve","message":"Please resolve","level":"WARNING"}
"""
    When the client sends a PATCH request to "/alerts/{last}/resolve"
    Then the response status code is 200
    And the response body contains "resolved" equal to "true"

  Scenario: Toggling an alert rule on and off
    Given the client sends a POST request to "/alerts/rules" with body:
      """
{"name":"Toggle rule","metric":"POWER","conditionType":"GREATER_THAN","threshold":500,"level":"WARNING","scopeType":"GENERAL","evaluatorType":"ACTIVE_POWER"}
"""
    When the client sends a PATCH request to "/alerts/rules/{last}/toggle"
    Then the response status code is 200
    And the response body contains "enabled" equal to "false"

  Scenario: Reading the notification preferences
    When the client sends a GET request to "/notifications/preferences"
    Then the response status code is 200
    And the response body contains a boolean "monthlyReportEnabled"

  Scenario: Disabling monthly report emails from the preferences
    When the client sends a PUT request to "/notifications/preferences" with body:
      """
{"monthlyReportEnabled":false}
"""
    Then the response status code is 200
    And the response body contains "monthlyReportEnabled" equal to "false"
