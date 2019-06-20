import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.version

plugins {
  id("com.eden.orchidPlugin") version Versions.orchid
}

dependencies {
  fun orchid(module: String) = "io.github.javaeden.orchid:Orchid$module:${Versions.orchid}"

  orchidRuntime(orchid("BsDoc"))
  orchidRuntime(orchid("Kotlindoc"))
  orchidRuntime(orchid("PluginDocs"))
  orchidRuntime(orchid("Wiki"))
  orchidRuntime(orchid("Pages"))
}


orchid {
  theme = "BsDoc"
}
