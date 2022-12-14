import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.1.2"
//    id("com.gradle.plugin-publish") version "1.0.0-rc-1"
    `java`
    `java-gradle-plugin`
    `maven-publish`
}

base {
    version = "1.0.1"
    group = "com.ssblur.mtp"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}


tasks {
    shadowJar {
        archiveClassifier.set("")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

gradlePlugin {
    plugins {
        create("mtp") {
            id = "com.ssblur.mtp"
            implementationClass = "com.ssblur.mtp.MarkdownToPatchouliPlugin"
            displayName = "Markdown to Patchouli"
            description = "A Gradle plugin for generating Patchouli books from Markdown documentation"
        }
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            groupId = "com.ssblur"
            artifactId = "mtp"
            version = "1.0.1"
        }
    }

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/ssblur/markdown-to-patchouli")
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
