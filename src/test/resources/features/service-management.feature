@service-management
Feature: Service Management
  Background:
    Given the RESTful API is available at base path "/api/v1"
    And the client is authenticated as "carlos.mendoza@example.com"

  Scenario: Opening a support ticket
    When the client sends a POST request to "/support-tickets" with body:
      """
{"subject":"No puedo vincular mi dispositivo","description":"El enchufe no aparece.","priority":"HIGH"}
"""
    Then the response status code is 201
    And the response body contains "status" equal to "OPEN"

  Scenario: Listing support tickets
    Given the client sends a POST request to "/support-tickets" with body:
      """
{"subject":"Support","description":"Need assistance","priority":"MEDIUM"}
"""
    When the client sends a GET request to "/support-tickets"
    Then the response status code is 200
    And the response body is a list with 1 item

  Scenario: Changing the status of a support ticket
    Given the client sends a POST request to "/support-tickets" with body:
      """
{"subject":"Status","description":"Track progress","priority":"MEDIUM"}
"""
    When the client sends a PATCH request to "/support-tickets/{last}/status" with body:
      """
{"status":"IN_PROGRESS"}
"""
    Then the response status code is 200
    And the response body contains "status" equal to "IN_PROGRESS"

  Scenario: Scheduling a maintenance ticket for a device
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Maintenance Device","type":"SMART_PLUG","powerWatts":60}
      """
    And the last created resource is remembered as "device"
    When the client sends a POST request to "/maintenance-tickets" with body:
      """
{"deviceId":{device},"deviceName":"Maintenance Device","type":"PREVENTIVE","description":"Revision preventiva","scheduledDate":"2030-12-31"}
      """
    Then the response status code is 201
    And the response body contains "status" equal to "OPEN"

  Scenario: Closing a maintenance ticket
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Closable Device","type":"SMART_PLUG","powerWatts":60}
      """
    And the last created resource is remembered as "device"
    And the client sends a POST request to "/maintenance-tickets" with body:
      """
{"deviceId":{device},"deviceName":"Closable Device","type":"PREVENTIVE","description":"Revision preventiva","scheduledDate":"2030-12-31"}
      """
    When the client sends a PATCH request to "/maintenance-tickets/{last}/status" with body:
      """
{"status":"COMPLETED"}
"""
    Then the response status code is 200
    And the response body contains "status" equal to "COMPLETED"
