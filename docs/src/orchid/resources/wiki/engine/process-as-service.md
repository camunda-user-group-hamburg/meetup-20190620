# Execute Process as Service

Ein gestarteter Process kann direkt ein Ergebniss zurückliefern.
Vergessenes Feature von 7.10 ... 

```
runtimeService.createProcessInstanceByKey("ggt")
        .setVariable("a", a)
        .setVariable("b", b)
        .executeWithVariablesInReturn()
```

## Demo

* aktiviere ProcessAsService

Größter gemeinsamer Teiler (Euklid)

execute: [ggt(15, 10)](http://localhost:8090/process/ggt/15/10)

