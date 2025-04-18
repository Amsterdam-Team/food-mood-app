plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.insert-koin:koin-core:4.0.2")
<<<<<<< HEAD
    implementation(kotlin("stdlib"))

=======
    implementation("com.jsoizo:kotlin-csv-jvm:1.10.0")
>>>>>>> feature/parse-file
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}