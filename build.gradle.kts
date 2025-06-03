plugins {
    kotlin("jvm") version "2.1.20"
    id("jacoco")
}

group = "planmate"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}


val includedPackages = listOf(
    "**/data/**",
    "**/domain/**",
)

val excludedPackages = listOf(
    "**/dependencyInjection/**",
    "**/domain/model/**",
)

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    classDirectories.setFrom(
        fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
            include(includedPackages)
            exclude(excludedPackages)
        }
    )
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
    violationRules {
        rule {
            limit {
                minimum = "0.80".toBigDecimal()
            }
        }
    }

    classDirectories.setFrom(
        fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
            include(includedPackages)
            exclude(excludedPackages)
        }
    )
}
