import net.fabricmc.loom.task.RemapJarTask
import org.ajoberstar.grgit.Grgit

plugins {
    id("fabric-loom").version("0.11-SNAPSHOT")
    kotlin("jvm") version "1.5.10"
    id("org.ajoberstar.grgit").version("3.1.1")
}
apply(plugin = "org.ajoberstar.grgit")

val gitHash = (project.ext.get("grgit") as Grgit).head().abbreviatedId
val semVer = "${project.version}+$gitHash"
val jarName = "BetterFoliage-$semVer-Fabric-${properties["mcVersion"]}"

repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://minecraft.curseforge.com/api/maven")
    maven("https://maven.modmuss50.me/")
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases")
    maven("https://jitpack.io")
}

dependencies {
    "minecraft"("com.mojang:minecraft:${properties["mcVersion"]}")
    "mappings"("net.fabricmc:yarn:${properties["yarnMappings"]}:v2")

    // basic Fabric stuff
    "modImplementation"("net.fabricmc:fabric-loader:${properties["loaderVersion"]}")
    "modImplementation"("net.fabricmc.fabric-api:fabric-api:${properties["fabricVersion"]}")
    "modImplementation"("net.fabricmc:fabric-language-kotlin:${properties["fabricKotlinVersion"]}")

    // configuration handling
    "modImplementation"("com.terraformersmc:modmenu:${properties["modMenuVersion"]}")
    listOf("modImplementation", "include").forEach { configuration ->
        configuration("me.shedaniel.cloth:cloth-config-fabric:${properties["clothConfigVersion"]}")
        configuration("me.zeroeightsix:fiber:${properties["fiberVersion"]}")
        configuration("com.github.pyrofab:fibertocloth:${properties["fiberToClothVersion"]}")
    }

    // Canvas Renderer
//    "modImplementation"("grondag:canvas:0.7.+")

    // Optifabric
//    "modImplementation"("com.github.modmuss50:OptiFabric:1.0.0")
    "implementation"("org.zeroturnaround:zt-zip:1.13")
}

sourceSets {
    get("main").ext["refMap"] = "betterfoliage.refmap.json"
}

loom {
    accessWidenerPath.set(file("src/main/resources/betterfoliage.accesswidener"))
}

kotlin {
    target.compilations.configureEach {
        kotlinOptions.freeCompilerArgs += listOf("-Xno-param-assertions", "-Xno-call-assertions")
    }
}

tasks.getByName<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") { expand(mutableMapOf("version" to semVer)) }
}

tasks.getByName<RemapJarTask>("remapJar") {
    archiveFileName.set("$jarName.jar")
}