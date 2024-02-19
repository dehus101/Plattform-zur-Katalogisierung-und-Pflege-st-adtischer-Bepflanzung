tasks.register("${ApplicationPlugin.TASK_RUN_NAME}Containerized") {
    description = "Runs this project as a Docker Compose project."
    group = ApplicationPlugin.APPLICATION_GROUP
    dependsOn(
        providers
            .gradleProperty("de.hhu.cs.dbs.dbwk.project.api")
            .map { ":${project.name}:$it:$name" }
            .get())
}
