package de.cughh.meetup

import mu.KLogging
import org.camunda.bpm.engine.ManagementService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.Variables
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.temporal.ChronoUnit.DAYS
import java.util.*

@RestController
@ConditionalOnProperty(name = ["feature.recalculateTimer"], havingValue = "true", matchIfMissing = false)
class WithTimerProcess(
    val runtimeService: RuntimeService,
    val managementService: ManagementService
) {
  companion object : KLogging()

  /**
   * Start a new process
   */
  @GetMapping("/process/timer/start")
  fun start(): ResponseEntity<String> {
    val initialArrival = Instant.now().plus(10, DAYS)


    val processInstance = runtimeService.startProcessInstanceByKey("process_with_timer",
        Variables.putValue("arrivalDate", initialArrival.toDate()))

          logger.info { "started ${processInstance.id} with arrival $initialArrival" }
        return  ResponseEntity.ok(processInstance.id)

  }

  /**
   * Change arrival date and recalculate timer.
   */
  @GetMapping("/process/timer/{pid}/reduce")
  fun reduce(@PathVariable("pid") pid: String): String {
    val job = getTimer(pid)

    val newArrival = (runtimeService.getVariable(pid, "arrivalDate") as Date).toInstant().minus(2, DAYS)
    runtimeService.setVariable(pid, "arrivalDate", newArrival.toDate())

    logger.info { """
      Reduce:
      =========

      Timer was set to ${job.duedate.toInstant()}


    """.trimIndent() }

    managementService.recalculateJobDuedate(job.id, true)

    val newTimerDue = getTimer(pid).duedate

    return "New Timer = $newTimerDue"
  }

  private fun getTimer(pid:String) = managementService.createJobQuery()
      .active()
      .activityId("timer")
      .processInstanceId(pid)
      .singleResult()

  /**
   * The due date is always 2 days before arrival
   */
  fun calculateTimerDate(execution: DelegateExecution): Date {
    val arrivalDate = (execution.getVariable("arrivalDate") as Date).toInstant()

    val dueDate = arrivalDate.minus(2, DAYS)

    logger.info {
      """

      Timer set to:  $dueDate


      """.trimIndent()
    }

    return dueDate.toDate()
  }

  private fun Instant.toDate() = Date.from(this)
}