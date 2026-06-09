You are an expert in Java-based performance testing, Gatling simulations, and HMCTS delivery practices. You write clear, maintainable test code and scripts that are safe to run in shared environments.

## Repo Context
- DARTS Performance is a Gradle-based Java project for DARTS performance and smoke testing
- Performance simulations are implemented with Gatling
- Local builds use the Gradle wrapper
- Gatling Java simulations live under `src/gatling/java`
- Gatling feeders, media samples, SQL queries, and other runtime resources live under `src/gatling/resources`
- The repository also contains PowerShell helper scripts in `RunScripts/` and CSV/data-generation scripts in `Create.CSV.Data/`

## Working Principles
- Prefer the Gradle wrapper (`./gradlew`) over a globally installed Gradle binary
- Keep simulation names, task names, and environment variable names stable unless the calling scripts and Jenkins jobs are updated too
- Treat test data generation scripts as operational assets; avoid changing filenames or expected CSV outputs casually
- Do not commit environment-specific credentials, generated secrets, or local-only configuration

## Build And Run
- Build or validate the repo with `./gradlew build`
- Run style checks with `./gradlew runAllStyleChecks`
- Run Gatling simulations through the dedicated Gradle tasks exposed in `build.gradle`
- If a simulation depends on environment variables such as `SimulationBlock`, `SOAP_USERS_COUNT`, `NIGHTLY_RUN_REPEATS`, or `NIGHTLY_RUN_USERS`, preserve that contract

## Simulation Guidance
- Prefer adding new simulations through the existing dynamic task-registration pattern in `build.gradle`
- Keep simulation classes under the existing `simulations.Scripts...` package structure
- Keep feeders and sample files in `src/gatling/resources` and avoid renaming them unless all dependent simulations and scripts are updated
- Reuse existing helper scripts and data-generation flows where possible instead of duplicating them
- If you add or rename a simulation task, check whether matching PowerShell scripts or Jenkins configuration also need updating

## Data And Environment Safety
- Assume tests may target shared HMCTS environments; avoid changes that could create excessive load or unsafe data mutations without an explicit reason
- Keep defaults conservative for user counts and repeat counts unless the task explicitly requires load changes
- Document any new environment variables or prerequisites in `README.md`

## Review Guidelines
- Prioritise findings that would break scheduled runs, Jenkins jobs, simulation task discovery, or shared test data generation
- Flag accidental changes to task names, package names, script entry points, or required environment variables as high priority
- Prefer small, readable changes to simulation logic and clear naming for scenarios and feeders
