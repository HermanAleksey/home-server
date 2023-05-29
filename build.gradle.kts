val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val postgres_version : String by project
val h2_version : String by project

plugins {
    kotlin("jvm") version "1.8.21"
    id("io.ktor.plugin") version "2.3.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21"
}

group = "com.parokq"
version = "0.0.1"
application {
    mainClass.set("com.parokq.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

ktor {
    docker {
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)

        localImageName.set("home-docker-image")
        imageTag.set("0.0.1-preview")

        portMappings.set(listOf(
            io.ktor.plugin.features.DockerPortMapping(
                outsideDocker = 80,
                insideDocker = 8080,
                protocol = io.ktor.plugin.features.DockerPortMappingProtocol.TCP
            )
        ))
    }
    fatJar {
        archiveFileName.set("fat.jar")
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}


task("do shit"){
    //docker build .
    //docker images -> take new
    //docker run -e DATABASE_SERVER=jdbc:h2:mem:test -dp 8080:8580 36f1c123233a
    println("im doing")
}