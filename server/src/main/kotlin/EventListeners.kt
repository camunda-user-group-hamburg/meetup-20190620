package de.cughh.meetup

import mu.KLogging
import org.camunda.bpm.engine.delegate.DelegateTask
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.time.Instant.now
import java.time.temporal.ChronoUnit
import java.util.*


@Component
@ConditionalOnProperty(name = ["feature.logHistoric"], havingValue = "true", matchIfMissing = false)
class HistoryLogger {
  companion object  : KLogging()

  @Async
  @EventListener(condition = """#evt.class.simpleName.startsWith("Historic")""")
  fun handle(evt: Any) {
    logger.info { "received: $evt" }
  }

}

@Component
@ConditionalOnProperty(name = ["feature.onTaskCreate"], havingValue = "true", matchIfMissing = false)
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