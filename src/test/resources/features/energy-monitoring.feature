@energy-monitoring
Feature: Energy Monitoring
  Background:
    Given the RESTful API is available at base path "/api/v1/energy-readings"
    And the client is authenticated as "carlos.mendoza@example.com"

  Scenario: Retrieving the energy dashboard summary
    Given the client sends a POST request to "" with body:
      """
{"deviceId":101,"deviceName":"Meter","watts":125.5}
"""
    When the client sends a GET request to "/dashboard-summary"
    Then the response status code is 200
    And the response body contains a numeric "currentWatts"
    And the response body contains an array "trend"

  Scenario: Recording simulated telemetry
    Given the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Telemetry Device","type":"SMART_PLUG","powerWatts":80}
      """
    And the last created resource is remembered as "device"
    And the RESTful API is available at base path "/api/v1/energy-readings"
    When the client sends a POST request to "" with body:
      """
{"deviceId":{device},"deviceName":"Telemetry Device","watts":80}
      """
    Then the response status code is 200
    And the response body contains "deviceName" equal to "Telemetry Device"

  Scenario: Retrieving the current sampling settings
    When the client sends a GET request to "/sampling-settings"
    Then the response status code is 200
    And the response body contains a numeric "sampleSeconds"

  Scenario: Updating the sampling interval
    When the client sends a PATCH request to "/sampling-settings" with body:
      """
{"sampleSeconds":30}
"""
    Then the response status code is 200
    And the response body contains "sampleSeconds" equal to "30"
