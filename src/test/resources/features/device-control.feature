@device-control
Feature: Device Control
  Background:
    Given the RESTful API is available at base path "/api/v1"
    And the client is authenticated as "carlos.mendoza@example.com"

  Scenario: Registering a new device
    When the client sends a POST request to "/devices" with body:
      """
{"name":"Sala Lamp","room":"Living Room","type":"SMART_PLUG","powerWatts":60.0}
"""
    Then the response status code is 201
    And the response body contains a non-empty "id"
    And the response body contains "status" equal to "OFF"

  Scenario: Registering a device is rejected without a name
    When the client sends a POST request to "/devices" with body:
      """
{"name":"","type":"SMART_PLUG","powerWatts":60.0}
"""
    Then the response status code is 400

  Scenario: Registering a device is rejected with non-positive power
    When the client sends a POST request to "/devices" with body:
      """
{"name":"Sala Lamp","type":"SMART_PLUG","powerWatts":-5.0}
"""
    Then the response status code is 400

  Scenario: Listing all devices of the user
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Device 1","type":"SMART_PLUG","powerWatts":10}
"""
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Device 2","type":"SMART_PLUG","powerWatts":20}
"""
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Device 3","type":"SMART_PLUG","powerWatts":30}
"""
    When the client sends a GET request to "/devices"
    Then the response status code is 200
    And the response body is a list with 3 items

  Scenario: Toggling a device from OFF to ON
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Toggle Device","type":"SMART_PLUG","powerWatts":60}
"""
    When the client sends a PATCH request to "/devices/{last}/toggle"
    Then the response status code is 200
    And the response body contains "status" equal to "ON"

  Scenario: Setting a device status explicitly
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Status Device","type":"SMART_PLUG","powerWatts":60}
"""
    When the client sends a PATCH request to "/devices/{last}/status" with body:
      """
{"status":"ON"}
"""
    Then the response status code is 200
    And the response body contains "status" equal to "ON"

  Scenario: Deleting a device
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Disposable Device","type":"SMART_PLUG","powerWatts":60}
"""
    When the client sends a DELETE request to "/devices/{last}"
    Then the response status code is 204

  Scenario: Creating a scheduled routine that turns a device off at night
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Bedroom Device","type":"SMART_PLUG","powerWatts":60}
"""
    And the last created resource is remembered as "device"
    When the client sends a POST request to "/routines" with body:
      """
{"deviceId":{device},"targetType":"DEVICE","targetId":{device},"name":"Dormir","action":"TURN_OFF","time":"23:00","repeatType":"DAILY"}
"""
    Then the response status code is 201
    And the response body contains "name" equal to "Dormir"
    And the response body contains "action" equal to "TURN_OFF"

  Scenario: Enabling and disabling a routine
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Routine Device","type":"SMART_PLUG","powerWatts":60}
"""
    And the last created resource is remembered as "device"
    And the client sends a POST request to "/routines" with body:
      """
{"deviceId":{device},"targetType":"DEVICE","targetId":{device},"name":"Routine","action":"TURN_ON","time":"08:00","repeatType":"DAILY"}
"""
    When the client sends a PATCH request to "/routines/{last}/status" with body:
      """
{"enabled":false}
"""
    Then the response status code is 200
    And the response body contains "enabled" equal to "false"

  Scenario: Executing a routine on demand
    Given the client sends a POST request to "/devices" with body:
      """
{"name":"Execution Device","type":"SMART_PLUG","powerWatts":60}
"""
    And the last created resource is remembered as "device"
    And the client sends a POST request to "/routines" with body:
      """
{"deviceId":{device},"targetType":"DEVICE","targetId":{device},"name":"Execute","action":"TURN_ON","time":"08:00","repeatType":"DAILY"}
"""
    When the client sends a PATCH request to "/routines/{last}/execute"
    Then the response status code is 200

  Scenario: Creating a device group with member devices
    Given the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/locations" with body:
      """
{"name":"Group Site","type":"OFFICE"}
      """
    And the last created resource is remembered as "location"
    And the client sends a POST request to "/rooms" with body:
      """
{"locationId":{location},"name":"Group Room","floor":"1"}
      """
    And the last created resource is remembered as "room"
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Group Device 1","type":"SMART_PLUG","powerWatts":20}
      """
    And the last created resource is remembered as "device1"
    And the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/device-assignments" with body:
      """
{"locationId":{location},"roomId":{room},"deviceId":{device1}}
      """
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Group Device 2","type":"SMART_PLUG","powerWatts":20}
      """
    And the last created resource is remembered as "device2"
    And the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/device-assignments" with body:
      """
{"locationId":{location},"roomId":{room},"deviceId":{device2}}
      """
    And the RESTful API is available at base path "/api/v1"
    When the client sends a POST request to "/device-groups" with body:
      """
{"name":"Iluminacion Sala","description":"Lights","deviceIds":[{device1},{device2}]}
"""
    Then the response status code is 201
    And the response body contains "name" equal to "Iluminacion Sala"

  Scenario: Executing a group action turns off all member devices simultaneously
    Given the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/locations" with body:
      """
{"name":"Action Site","type":"OFFICE"}
      """
    And the last created resource is remembered as "location"
    And the client sends a POST request to "/rooms" with body:
      """
{"locationId":{location},"name":"Action Room","floor":"1"}
      """
    And the last created resource is remembered as "room"
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Active Device 1","type":"SMART_PLUG","powerWatts":20}
      """
    And the last created resource is remembered as "device1"
    And the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/device-assignments" with body:
      """
{"locationId":{location},"roomId":{room},"deviceId":{device1}}
      """
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Active Device 2","type":"SMART_PLUG","powerWatts":20}
      """
    And the last created resource is remembered as "device2"
    And the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/device-assignments" with body:
      """
{"locationId":{location},"roomId":{room},"deviceId":{device2}}
      """
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/device-groups" with body:
      """
{"name":"Active Group","deviceIds":[{device1},{device2}]}
"""
    When the client sends a PATCH request to "/device-groups/{last}/execute" with body:
      """
{"status":"OFF"}
"""
    Then the response status code is 200

  Scenario: Previewing an operation mode before activation
    Given the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/locations" with body:
      """
{"name":"Mode Site","type":"OFFICE"}
      """
    And the last created resource is remembered as "location"
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Mode Device","type":"SMART_PLUG","powerWatts":60}
      """
    And the last created resource is remembered as "device"
    And the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/device-assignments" with body:
      """
{"locationId":{location},"deviceId":{device}}
      """
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/operation-modes" with body:
      """
{"locationId":{location},"name":"Away","allDay":true,"internalRoutines":[{"name":"Away off","targetType":"DEVICE","targetId":{device},"action":"TURN_OFF","triggerTime":"00:00","enabled":true}]}
      """
    And the response status code is 201
    And the client sends a GET request to "/operation-modes"
    And the first response item is remembered as "mode"
    When the client sends a GET request to "/operation-modes/{mode}/preview"
    Then the response status code is 200

  Scenario: Activating an operation mode
    Given the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/locations" with body:
      """
{"name":"Activation Site","type":"OFFICE"}
      """
    And the last created resource is remembered as "location"
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/devices" with body:
      """
{"name":"Activation Device","type":"SMART_PLUG","powerWatts":60}
      """
    And the last created resource is remembered as "device"
    And the RESTful API is available at base path "/api/v1/workplace"
    And the client sends a POST request to "/device-assignments" with body:
      """
{"locationId":{location},"deviceId":{device}}
      """
    And the RESTful API is available at base path "/api/v1"
    And the client sends a POST request to "/operation-modes" with body:
      """
{"locationId":{location},"name":"Home","allDay":true,"internalRoutines":[{"name":"Home on","targetType":"DEVICE","targetId":{device},"action":"TURN_ON","triggerTime":"00:00","enabled":true}]}
      """
    And the response status code is 201
    And the client sends a GET request to "/operation-modes"
    And the first response item is remembered as "mode"
    When the client sends a PATCH request to "/operation-modes/{mode}/activate"
    Then the response status code is 200
