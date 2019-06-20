# How to setup

## Repository

```
 // note: for camunda-ee to work, add  credentials to $HOME/.gradle/gradle.properties
  maven("https://app.camunda.com/nexus/content/repositories/camunda-bpm-ee") {
    name = "camunda-bpm-ee"
    credentials {
      username = properties["camundaRepoUser"] as String
      password = properties["camundaRepoPassword"] as String
    }
  }
```

dazu in `$HOME/.gradle/gradle.properties`:

```
camundaRepoUser=<USER>
camundaRepoPassword=<PASSWORD>

```

## License Key

Den camunda license key ablegen in:

* `$HOME/.camunda/license.txt`


