plugins {
    id("java")
    id("application")
    kotlin("jvm") version "1.9.10"
}

group = "org.example"
version = "1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    implementation("org.yaml:snakeyaml:2.2")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("com.itextpdf:itext7-core:7.2.4")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

