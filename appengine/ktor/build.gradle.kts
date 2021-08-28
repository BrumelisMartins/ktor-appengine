plugins {
    kotlin("jvm") version "1.4.20"
    war
    id("com.google.cloud.tools.appengine") version "2.4.1"
}

repositories {
    mavenCentral()
    // kotlinx-html-jvm is not available in mavenCentral yet
    // See https://github.com/Kotlin/kotlinx.html/issues/173
    jcenter {
        content {
            includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
        }
    }
}

require (JavaVersion.current() <= JavaVersion.VERSION_11) {
    "AppEngine supports only Java 8 or 11 for now, the current Java is ${JavaVersion.current()}"
}

dependencies {



    implementation("com.google.cloud:google-cloud-logging-logback:0.117.0-alpha")

    implementation ("ch.qos.logback:logback-classic:1.2.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")


    implementation(platform("io.ktor:ktor-bom:1.5.2"))
    implementation("io.ktor:ktor-server-servlet")
    implementation("io.ktor:ktor-html-builder")
    implementation ("io.ktor:ktor-locations:1.5.2")
    implementation ("io.ktor:ktor-server-sessions:1.5.2")
    implementation ("io.ktor:ktor-auth:1.5.2")
    implementation ("io.ktor:ktor-auth-jwt:1.5.2")
    implementation ("io.ktor:ktor-gson:1.5.2")
    implementation ("io.ktor:ktor-serialization:1.5.2")
    implementation ("io.ktor:ktor-server-host-common:1.5.2")
    implementation("com.google.cloud.sql:postgres-socket-factory:1.0.15")

    compile ("org.jetbrains.exposed:exposed-core:0.33.1")
    compile ("org.jetbrains.exposed:exposed-dao:0.33.1")
    compile ("org.jetbrains.exposed:exposed-jdbc:0.33.1")
    compile ("org.postgresql:postgresql:42.2.18")
    compile ("com.zaxxer:HikariCP:3.3.1")


    runtimeOnly("com.google.appengine:appengine:1.9.88")
}

appengine {
    deploy {
        projectId = "GCLOUD_CONFIG"
        version = "GCLOUD_CONFIG"
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    register("run") {
        dependsOn("appengineRun")
    }
}
