# SentinelAI

A Java 21 / Spring Boot foundation for an incident detection and root-cause analysis platform.

## Requirements

- JDK 21
- Maven (or IntelliJ IDEA's bundled Maven support)

## Run from IntelliJ

1. Extract this ZIP into a **new folder** (do not extract over the old project).
2. Open the extracted `sentinelai` folder in IntelliJ.
3. When prompted, trust/open the project and choose **Open as Project**.
4. Allow IntelliJ to import the project as a Maven project.
5. Set Project SDK to JDK 21.
6. Open `SentinelaiApplication.java` and click Run.

## Run with Maven

If a Maven Wrapper is present, use:

```powershell
.\mvnw.cmd spring-boot:run
```

Otherwise, from a terminal with Maven installed:

```powershell
mvn spring-boot:run
```

## Verify

- Application endpoint: http://localhost:8080/
- Health endpoint: http://localhost:8080/actuator/health

Expected root response:

```json
{
  "application": "SentinelAI",
  "status": "running",
  "message": "Incident analysis service is ready"
}
```

## Why PostgreSQL/JPA are not enabled yet

This clean foundation is intentionally runnable before a database is started. We will add the PostgreSQL driver, datasource configuration, and JPA entities after the IDE launches this base project successfully.
