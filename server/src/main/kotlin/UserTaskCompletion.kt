package de.cughh.meetup

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.DelegateTask
import org.camunda.bpm.engine.variable.Variables
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.event.EventListener
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task/calculate")
@ConditionalOnProperty(name = ["feature.userTaskCompletion"], havingValue = "true", matchIfMissing = false)
class CalculateTaskController(val taskService: TaskService, val runtimeService: RuntimeService) {

  @GetMapping("/start")
  fun start(@RequestParam("price") price: Long): ResponseEntity<String> {
    val processInstance = runtimeService.startProcessInstanceByKey("calculator", Variables.putValue("price", price))

    val task = taskService.createTaskQuery().processInstanceId(processInstance.id).singleResult()!!

    return ResponseEntity.ok("""{"taskId":"${task.id}"}""")
  }

  @GetMapping("/{taskId}/complete")
  fun completeWithVariable(@PathVariable("taskId") taskId: String, @RequestParam("count") count: Long): ResponseEntity<Map<String, Any>> {
    val variableMap = taskService.completeWithVariablesInReturn(
        taskId,
        Variables.putValue("count", count),
        true
    )

    return ResponseEntity.ok().body(variableMap)
  }

  @EventListener(condition = "#task.eventName.equals('complete') && #task.taskDefinitionKey.equals('task_multiply')") // TODO: this could be nicer!
  fun onComplete(task: DelegateTask) = with(task) {
    val price = getVariable("price") as Long
    val count = getVariable("count") as Long

    setVariable("result", price*count)
  }
}
