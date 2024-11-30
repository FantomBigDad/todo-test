plugins {
    id("java")
    kotlin("jvm") version "1.9.0" // Подключение Kotlin JVM плагина
    id("io.qameta.allure") version "2.11.2" // Плагин Allure
}
java {
    sourceCompatibility = JavaVersion.VERSION_15
    targetCompatibility = JavaVersion.VERSION_15
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // Укажите вашу версию Java
    }
}

group = "org.example.todo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // RestAssured: библиотека для тестирования REST API
    // RestAssured JSON parser (если используете Jackson)
    implementation("io.rest-assured:rest-assured:5.3.0")
    implementation("io.rest-assured:json-path:5.3.0")
    implementation("io.rest-assured:xml-path:5.3.0")
    implementation("io.rest-assured:json-schema-validator:5.3.0") // Для работы с JSON схемами
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    // Jackson для JSON сериализации/десериализации
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("org.json:json:20230227")
    // JUnit 5: для написания и запуска тестов
    implementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // Allure: генерация отчетов
    implementation("io.qameta.allure:allure-junit5:2.21.0")

    // Jackson или Gson для сериализации (выберите одну по необходимости)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.google.code.gson:gson:2.10.1")

    // Apache Commons Lang (опционально для работы с StringUtils)
    implementation("org.apache.commons:commons-lang3:3.13.0")

    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("allure.results.directory", file("build/allure-results"))
}

