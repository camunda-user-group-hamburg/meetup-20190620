# Spring EventHandler

Camunda published alle

* Lifecycle Hooks
  * TaskListener[create|assign|complete|cancel]
  * ExecutionListener[start|end|take]
* Historic events
  * HistoricTaskInstance
  * HistoricProcessInstance
  * ...
  
per Spring `ApplicationEventPublisher`.
  
Die [camunda-bpm-reactor](https://github.com/camunda/camunda-bpm-reactor) Extension ist damit im Spring Boot
Umfeld hinfällig.

## Log

```
--- [main] org.camunda.bpm.spring.boot              : STARTER-SB020 ProcessApplication enabled: autoDeployment via springConfiguration#deploymentResourcePattern is disabled
--- [main] o.c.b.s.b.s.event.EventPublisherPlugin   : EVENTING-001: Initialized Camunda Spring Boot Eventing Engine Plugin.
--- [main] o.c.b.s.b.s.event.EventPublisherPlugin   : EVENTING-003: Task events will be published as Spring Events.
--- [main] o.c.b.s.b.s.event.EventPublisherPlugin   : EVENTING-005: Execution events will be published as Spring Events.
--- [main] o.c.b.s.b.s.event.EventPublisherPlugin   : EVENTING-007: History events will be published as Spring events.
--- [main] org.camunda.bpm.engine                   : ENGINE-00001 Process Engine default created.
```

und historische events:

```
--- [main] de.cughh.meetup.HistoryLogger            : received: HistoricActivityInstanceEventEntity[activityId=task_foo, activityName=Do foo, activityType=userTask, activityInstanceId=task_foo:1ba2d423-9360-11e9-9da8-acde48001122, activityInstanceState=0, parentActivityInstanceId=1b9eb571-9360-11e9-9da8-acde48001122, calledProcessInstanceId=null, calledCaseInstanceId=null, taskId=null, taskAssignee=null, durationInMillis=null, startTime=Thu Jun 20 15:34:20 CEST 2019, endTime=null, eventType=start, executionId=1b9eb571-9360-11e9-9da8-acde48001122, processDefinitionId=process_foo:1:1b9c9290-9360-11e9-9da8-acde48001122, rootProcessInstanceId=1b9eb571-9360-11e9-9da8-acde48001122, processInstanceId=1b9eb571-9360-11e9-9da8-acde48001122, tenantId=null]
--- [main] de.cughh.meetup.HistoryLogger            : received: HistoricTaskInstanceEventEntity[taskId1ba34954-9360-11e9-9da8-acde48001122, assignee=null, owner=null, name=Do foo, description=null, dueDate=null, followUpDate=null, priority=50, parentTaskId=null, deleteReason=null, taskDefinitionKey=task_foo, durationInMillis=null, startTime=Thu Jun 20 15:34:20 CEST 2019, endTime=null, id=1ba34954-9360-11e9-9da8-acde48001122, eventType=create, executionId=1b9eb571-9360-11e9-9da8-acde48001122, processDefinitionId=process_foo:1:1b9c9290-9360-11e9-9da8-acde48001122, rootProcessInstanceId=1b9eb571-9360-11e9-9da8-acde48001122, processInstanceId=1b9eb571-9360-11e9-9da8-acde48001122, activityInstanceId=task_foo:1ba2d423-9360-11e9-9da8-acde48001122, tenantId=null]
--- [main] de.cughh.meetup.HistoryLogger            : received: HistoricActivityInstanceEventEntity[activityId=task_foo, activityName=Do foo, activityType=userTask, activityInstanceId=task_foo:1ba2d423-9360-11e9-9da8-acde48001122, activityInstanceState=0, parentActivityInstanceId=1b9eb571-9360-11e9-9da8-acde48001122, calledProcessInstanceId=null, calledCaseInstanceId=null, taskId=1ba34954-9360-11e9-9da8-acde48001122, taskAssignee=null, durationInMillis=null, startTime=Thu Jun 20 15:34:20 CEST 2019, endTime=null, eventType=update, executionId=1b9eb571-9360-11e9-9da8-acde48001122, processDefinitionId=process_foo:1:1b9c9290-9360-11e9-9da8-acde48001122, rootProcessInstanceId=1b9eb571-9360-11e9-9da8-acde48001122, processInstanceId=1b9eb571-9360-11e9-9da8-acde48001122, tenantId=null]

```

### Task

* wurde erzeugt und 
  * Admin assigned
  * candidateGroup `holisticon`
  * due in zwei Tagen
  


## TODO

Die SPEL für conditions ist nicht besonders schön:

```
@EventListener(condition = "#task.eventName.equals('complete') && #task.taskDefinitionKey.equals('task_multiply')")
  fun onComplete(task: DelegateTask) = with(task) {
```

verglichen mit camunda-bpm-reactor:

```
@CamundaSelector(type = "userTask", event = TaskListener.EVENTNAME_CREATE)
public class TaskCreateListener implements TaskListener {
```

da fehlt noch was. Zum Beispiel: [Using beans in condition](https://stackoverflow.com/questions/54576124/how-to-test-spring-event-listener-conditional-spel)