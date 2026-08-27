plugins {
    id("java-library")
    id("xyz.jpenilla.run-paper") version "3.1.0"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
    compileOnly("dev.piny:Write:1.0.0")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

val serverLauncher = javaToolchains.launcherFor {
    vendor = JvmVendorSpec.JETBRAINS
    languageVersion = JavaLanguageVersion.of(25)
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("26.2")
        javaLauncher = serverLauncher
        jvmArgs("-Xms2G", "-Xmx2G", "-XX:+AllowEnhancedClassRedefinition", "-Dcom.mojang.eula.agree=true")

        downloadPlugins {
//            modrinth("lKYpOWTi", "ungYTAmg") // Write
        }
    }

    processResources {
        val props = mapOf("version" to version, "description" to project.description)
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}
