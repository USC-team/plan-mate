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
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.4.0")
    implementation(platform("io.insert-koin:koin-bom:4.0.3"))
    implementation("io.insert-koin:koin-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.2")
    testImplementation("com.google.truth:truth:1.4.4")
    testImplementation("io.mockk:mockk:1.14.2")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

val includedPackages = listOf(
    "**/data/**",
    "**/domain/**"
)

val excludedPackages = listOf(
    "**/dependencyInjection/**",
    "**/domain/model/**"
)

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    classDirectories.setFrom(
        fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
            include(includedPackages)
            exclude(excludedPackages)
        }
    )
    sourceDirectories.setFrom(files("src/main/kotlin"))
    executionData.setFrom(files("${buildDir}/jacoco/test.exec"))
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)

    violationRules {
        rule {
            enabled = true
            element = "CLASS"

            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal() // 100% branch coverage
            }

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal() // 100% line coverage
            }
        }
    }

    classDirectories.setFrom(
        fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
            include(includedPackages)
            exclude(excludedPackages)
        }
    )
    sourceDirectories.setFrom(files("src/main/kotlin"))
    executionData.setFrom(files("${buildDir}/jacoco/test.exec"))
}
