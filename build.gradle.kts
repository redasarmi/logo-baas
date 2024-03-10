import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation ("org.mindrot:jbcrypt:0.4")

    implementation("io.ktor:ktor-server-netty:2.1.0")
    implementation("io.ktor:ktor-server-core:2.1.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.1.0")
    implementation("io.ktor:ktor-server-double-receive:2.1.0")

    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.0")
    implementation("com.google.code.gson:gson:2.8.8")


    implementation("org.jetbrains.exposed:exposed-core:0.37.3")
    implementation("org.jetbrains.exposed:exposed-dao:0.37.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.37.3")

    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}