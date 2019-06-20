# Was ist neu in Camunda 7.11?

[CUGHH](https://cughh.de) Meetup - 20.06.2019

for full docs, run `gradle :docs:orchidServe` and visit [localhost:8080](http://localhost:8080).

To use the examples, run the spring boot app `gradle :server:bootRun`

Cockpit is running on [localhost:8090](http://localhost:8090/app/welcome/default/#!/welcome)


## Agenda

* new version 7.11.0
* modeller rewrite: 3.1.0 (react)
* spring boot starter 3.3.1
* task completion
* process as a service
* external tasks java-api / springboot extension

### out of scope

* fluent test assertions - camunda-bpm-assert 4.0.0
* Trigger BPMN Errors from Execution and Task Listeners
* Java/REST API: Deleting Historic Variables
  * Delete Variable History in Cockpit Full (Enterprise)
* Return Variables on Message Correlation

```
historyService.deleteHistoricVariableInstance("aVariableInstanceId");
historyService.deleteHistoricVariableInstancesByProcessInstanceId("aProcessInstanceId");
```


## What else

* Fine-grained Permissions for Batches and Job Retries
* MariaDB/MySQL: Job Due Dates after 2038
* JBoss/Wildfly: Expressions in Camunda Subsystem
* Java/REST API: Deleting Historic Variables
* Change the Removal Time for Historic Processes
* Engine Wide History Time to Live
* More User Operation Log Entries
* Support for Password Policies
* Tasklist: Case-Insensitive Task Queries
* Java/REST API: Case Insensitive Semantics for Task Variables
* Operator Authorizations
* Operator and Admin Auditing
  


## Offizielle Release Notes:

* [Camunda 7.11.0](https://blog.camunda.com/post/2019/05/camunda-bpm-7110-released/)





