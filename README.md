# meetup-20190620

Meetup Session: Was ist neu in Camunda 7.11?


## Agenda



* Spring Boot Starter 3.3.1
  * uses sb 2.1.5.RELEASE


* Timer recalculation (+Cockpit)

```
managementService.recalculateJobDuedate("aJobId", true);// based on original creation date of the job

managementService.recalculateJobDuedate("aJobId", false);// based on current date
```

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



## TODO

* license file




## Notes


* enterprise server mit camumda und h2
* External tasks (2 worker) mit neuer client api und spring boot
* Rest auf prozess mit start/execute with variables



