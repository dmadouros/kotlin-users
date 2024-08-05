val kotlinVersion: String by project
val postgresVersion: String by project
val exposedVersion: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("org.liquibase.gradle") version "2.2.0"
}

group = "me.dmadouros"
version = "0.0.1"

application {
    mainClass.set("me.dmadouros.user.application.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-jackson-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")

    // Log4j2
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.22.0"))
    implementation("org.apache.logging.log4j:log4j-api")
    implementation("org.apache.logging.log4j:log4j-core")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl")

    // Postgresql
    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("com.zaxxer:HikariCP:5.1.0")

    // Michael Bull
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.18")
    implementation("com.michael-bull.kotlin-retry:kotlin-retry:1.0.9")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    // Liquibase
    implementation("com.mattbertolini:liquibase-slf4j:4.1.0")
    implementation("org.liquibase:liquibase-core:4.23.0")
    implementation("org.yaml:snakeyaml:2.1")
    liquibaseRuntime("info.picocli:picocli:4.7.4")
    liquibaseRuntime("org.liquibase:liquibase-core:4.23.0")
    liquibaseRuntime("org.postgresql:postgresql:$postgresVersion")
    liquibaseRuntime("org.yaml:snakeyaml:2.1")

    // Eventstore DB
    implementation("com.eventstore:db-client-java:4.1.1")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.28.0")
}

tasks.test {
    useJUnitPlatform()
}

liquibase {
    activities.register("main") {
        arguments =
            mapOf(
                "classpath" to "$projectDir/src/main/resources",
                "changelogFile" to "db/changelog/db.changelog-master.yml",
                "username" to "admin",
                "password" to "test",
                "url" to "jdbc:postgresql://localhost:5432/users",
            )
    }
}
