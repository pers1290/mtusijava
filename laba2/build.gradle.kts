import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties
import java.io.FileOutputStream

abstract class PrintInfoTask : DefaultTask() {
    
    @get:Input
    abstract val projectName: Property<String>
    
    @TaskAction
    fun print() {
        println("First!")
        println("Progekt: ${projectName.get()}")
        println("Gradle: ${project.gradle.gradleVersion}")
    }
}

tasks.register<PrintInfoTask>("printInfo") {
    group = "Custom"
    description = "Print info"
    projectName.set(project.name)
}


abstract class GenerateBuildPassportTask : DefaultTask() {
    
    @TaskAction
    fun generate() {
        val resourcesDir = project.rootDir.toPath()
            .resolve("app/src/main/resources")
        resourcesDir.toFile().mkdirs()
        
        val props = Properties()
        

        val userName = System.getenv("USERNAME") ?: "Unknown"
        props.setProperty("build.user", userName)

        props.setProperty("build.os", System.getProperty("os.name"))

        props.setProperty("build.java.version", System.getProperty("java.version"))

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        props.setProperty("build.time", LocalDateTime.now().format(formatter))

        props.setProperty("build.message", "Hello!")
        

        val file = resourcesDir.resolve("build-passport.properties")
        FileOutputStream(file.toFile()).use { out ->
            props.store(out, "Info")
        }
    }
}

tasks.register<GenerateBuildPassportTask>("generateBuildPassport") {
    group = "Build"
    description = "Add file build-passport.properties"
}

