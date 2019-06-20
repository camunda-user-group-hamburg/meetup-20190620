# Was ist neu in Camunda 7.11?

[CUGHH](https://cughh.de) Meetup - 20.06.2019

for full docs, run `gradle :docs:orchidServe` and visit [localhost:8080](http://localhost:8080).

To use the examples, run the spring boot app `gradle :server:bootRun`

Cockpit is running on [localhost:8090](http://localhost:8090/app/welcome/default/#!/welcome)


 ## Overview

* new version 7.11.0
* modeller rewrite: 3.1.0 (react)
* spring boot starter 3.3.1
* fluent test assertions - camunda-bpm-assert 4.0.0


Offizielle Release Notes:

* [Camunda 7.11.0](https://blog.camunda.com/post/2019/05/camunda-bpm-7110-released/)

----

## Agenda


* Java/REST API: Case Insensitive Semantics for Task Variables

* Complete Task with variables
* Start Process with variables

* camunda-bpm-assert 4.0.0

* external tasks java-api / springboot extension

* Trigger BPMN Errors from Execution and Task Listeners
* Java/REST API: Deleting Historic Variables
  * Delete Variable History in Cockpit Full (Enterprise)


```
historyService.deleteHistoricVariableInstance("aVariableInstanceId");
historyService.deleteHistoricVariableInstancesByProcessInstanceId("aProcessInstanceId");
```


* from https://blog.camunda.com/post/2019/05/camunda-bpm-7110-released/ 
  * Operator Authorizations
  * Operator and Admin Auditing
  * Return Variables on Task Completion / Form Submission / Message Correlation
  * Asynchronously set Removal Time on Historical Data
  * BPMN Error Event Triggering from Execution Listeners
  * Fluent Java Testing API
  * Additional Supported Environments



## Notes


* External tasks (2 worker) mit neuer client api und spring boot
* Rest auf prozess mit start/execute with variables



