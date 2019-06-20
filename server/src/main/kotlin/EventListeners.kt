package de.cughh.meetup

import mu.KLogging
import org.camunda.bpm.engine.delegate.DelegateTask
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.Instant.now
import java.time.temporal.ChronoUnit
import java.util.*


@Component
class HistoryLogger {
  companion object  : KLogging()

  @EventListener(condition = "#evt.getClass().getSimpleName().startsWith('Historic')")
  fun handle(evt: Any) {
    logger.info { "received: $evt" }
  }

}

@Component
class UserTaskHandler {
  companion object  : KLogging()

  @EventListener(condition = "#task.eventName.equals('create')")
  fun assignTask(task: DelegateTask) = with(task) {
    assignee = "admin"
    addCandidateGroup("holisticon")
    dueDate = Date.from(now().plus(2, ChronoUnit.DAYS))

    logger.info { "task created: ${task.taskDefinitionKey}" }
  }
}