plugins {
    id("java")
    // 暂时禁用checkstyle
    // id("checkstyle")
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

repositories {
    mavenCentral()
}

/*
checkstyle {
    configFile = file("${rootProject.projectDir}/resources/edc-checkstyle-config.xml")
    toolVersion = "10.12.7"
    maxWarnings = 0
}

dependencies {
    checkstyle("com.puppycrawl.tools:checkstyle:10.12.7")
    
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    
    // Nimbus JOSE+JWT for JWT/JWS
    implementation("com.nimbusds:nimbus-jose-jwt:9.37.3")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Checkstyle> {
    isShowViolations = true
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
*/

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    
    // Nimbus JOSE+JWT for JWT/JWS
    implementation("com.nimbusds:nimbus-jose-jwt:9.37.3")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// 配置JAR文件
tasks.bootJar {
    archiveFileName.set("did-vc-extension.jar")
}

// 这告诉Spring Boot在构建后直接运行应用
tasks.bootRun {
    mainClass.set("org.example.Application")
}