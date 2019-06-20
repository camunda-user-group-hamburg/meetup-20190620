package de.cughh.meetup

import mu.KLogging
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProcessGgtService(val runtimeService: RuntimeService) {
  companion object : KLogging()

  @GetMapping("/process/ggt/{a}/{b}")
  fun start(@PathVariable("a") a: Long, @PathVariable("b") b: Long) = runtimeService.createProcessInstanceByKey("ggt")
      .setVariable("a", a)
      .setVariable("b", b)
      .executeWithVariablesInReturn().variables

  @Bean
  fun ggtDelegate() = JavaDelegate { execution ->
    fun ggt(a: Long, b: Long): Long {
      if (b == 0L) return a
      return ggt(b, a % b)
    }

    with(execution) {
      val a = getVariable("a") as Long
      val b = getVariable("b") as Long
      setVariable("ggt", ggt(a, b))
    }
  }

  @EventListener
  fun deploy(evt: PostDeployEvent) {
    evt.processEngine.repositoryService.createDeployment()
        .addModelInstance("ggt.bpmn", Bpmn.createExecutableProcess("ggt")
            .camundaVersionTag("1")
            .startEvent()
            .serviceTask().name("Calculate GGT").camundaDelegateExpression("\${${ProcessGgtService::ggtDelegate.name}}")
            .endEvent()
            .done())
        .deploy().let {
          logger.info { "deployed process ggt: ${it.id}" }
        }

  }
}