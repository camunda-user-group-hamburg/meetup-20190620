package de.cughh.meetup

import mu.KLogger
import mu.KLogging
import org.camunda.bpm.engine.history.HistoricActivityInstance
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component


@SpringBootApplication
@EnableProcessApplication
class CamundaApplication

fun main(args:Array<String>) = runApplication<CamundaApplication>(*args).let { Unit }

@Component
class Starter {

  @EventListener
  fun onStart(evt: PostDeployEvent) {
    evt.processEngine.repositoryService.createDeployment()
        .addModelInstance("foo.bpmn", Bpmn.createExecutableProcess("process_foo")
            .camundaVersionTag("1")
            .camundaStartableInTasklist(false)
            .startEvent()
            .userTask("task_foo").name("Do foo")
            .endEvent()
            .done())
        .deploy()

    evt.processEngine.runtimeService.startProcessInstanceByKey("process_foo")
  }

}
