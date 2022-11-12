val versioning = "versioning"

tasks.register("printVersion") {
    group = versioning
    doLast { println(project.version) }
}