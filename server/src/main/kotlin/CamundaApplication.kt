package de.cughh.meetup

import mu.KLogging
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@SpringBootApplication
@EnableProcessApplication
@EnableAsync
@EnableScheduling
class CamundaApplication

fun main(args: Array<String>) = runApplication<CamundaApplication>(*args).let { Unit }


/**
 * Deploys generated foo process and starts new instance every 10 seconds.
 */
@Component
@ConditionalOnProperty(name = ["feature.startFooProcesses"], havingValue = "true", matchIfMissing = false)
class Starter(val runtimeService: RuntimeService) {

  companion object : KLogging()

  @EventListener
  fun deployFooProcess(evt: PostDeployEvent) = with(evt) {
    processEngine.repositoryService.createDeployment()
        .addModelInstance("foo.bpmn", Bpmn.createExecutableProcess("process_foo")
            .camundaVersionTag("1")
            .camundaStartableInTasklist(false)
            .startEvent()
            .userTask("task_foo").name("Do foo")
            .endEvent()
            .done())
        .deploy().let {
          logger.info { "deployed 'foo.bpmn' - ${it.id}" }
        }
  }

  @Scheduled(initialDelay = 10000, fixedDelay = 10000)
  fun startFooProcess() = runtimeService.startProcessInstanceByKey("process_foo").let {
    logger.info { "started foo instance - ${it.id} " }
  }

}
