# Timer recalculation

Timer können neu berechnet werden

```
managementService.recalculateJobDuedate("aJobId", true);// based on original creation date of the job

managementService.recalculateJobDuedate("aJobId", false);// based on current date
```
