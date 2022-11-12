plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.bom)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        create("mock-service") {
            id = "mock-service"
            displayName = "Mock Service"
            description = "Mock Service Plugin for starting WS in Gradle"
            implementationClass = "MockServicePlugin"
        }
    }
}
