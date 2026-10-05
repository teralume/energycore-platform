@workplace
Feature: Workplace Management
  Background:
    Given the RESTful API is available at base path "/api/v1/workplace"
    And the client is authenticated as "carlos.mendoza@example.com"

  Scenario: Creating a location
    When the client sends a POST request to "/locations" with body:
      """
{"name":"Local A","address":"Av. Los Proceres 123","type":"OFFICE"}
"""
    Then the response status code is 201
    And the response body contains "name" equal to "Local A"

  Scenario: Listing all locations of the user
    Given the client sends a POST request to "/locations" with body:
      """
{"name":"Local A","type":"OFFICE"}
"""
    And the client sends a POST request to "/locations" with body:
      """
{"name":"Local B","type":"STORE"}
"""
    When the client sends a GET request to "/locations"
    Then the response status code is 200
    And the response body is a list with 2 items

  Scenario: Updating a location
    Given the client sends a POST request to "/locations" with body:
      """
{"name":"Local A","type":"OFFICE"}
"""
    When the client sends a PATCH request to "/locations/{last}" with body:
      """
{"name":"Local A - Renovado"}
"""
    Then the response status code is 200
    And the response body contains "name" equal to "Local A - Renovado"

  Scenario: Deleting a location
    Given the client sends a POST request to "/locations" with body:
      """
{"name":"Disposable","type":"OFFICE"}
"""
    When the client sends a DELETE request to "/locations/{last}"
    Then the response status code is 204

  Scenario: Creating a room inside a location
    Given the client sends a POST request to "/locations" with body:
      """
{"name":"Warehouse","type":"WAREHOUSE"}
"""
    And the last created resource is remembered as "location"
    When the client sends a POST request to "/rooms" with body:
      """
{"locationId":{location},"name":"Almacen","floor":"1"}
"""
    Then the response status code is 201
    And the response body contains "name" equal to "Almacen"

  Scenario: Assigning a device to a room
    Given the client sends a POST request to "/locations" with body:
      """
{"name":"Assignment site","type":"OFFICE"}
"""
    And the last created resource is remembered as "location"
    And the client sends a POST request to "/rooms" with body:
      """
{"locationId":{location},"name":"Room","floor":"1"}
"""
    And the last created resource is remembered as "room"
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Assigned Device","type":"SMART_PLUG","powerWatts":60}
"""
    And the last created resource is remembered as "device"
    And the RESTful API is available at base path "/api/v1/workplace"
    When the client sends a POST request to "/device-assignments" with body:
      """
{"locationId":{location},"roomId":{room},"deviceId":{device}}
"""
    Then the response status code is 201
    And the response body contains "deviceId" equal to "{device}"
    And the response body contains "roomId" equal to "{room}"
