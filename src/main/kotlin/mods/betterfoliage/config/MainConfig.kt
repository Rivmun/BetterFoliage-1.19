package mods.betterfoliage.config

import io.github.fablabsmc.fablabs.api.fiber.v1.builder.ConfigTreeBuilder
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree
import java.util.*

interface PopulationConfigData {
    val enabled: Boolean
    val population: Int
    fun enabled(random: Random) = random.nextInt(64) < population && enabled
}

fun population(default: Int) = integer(default, min = 0, max = 64, langKey = recurring)

class MainConfig : DelegatingConfigGroup(ConfigTree.builder(null, "root")) {

    val enabled by boolean(true, langKey = fakeCategory("global"))
    val nVidia by boolean(true, langKey = fakeCategory("global"))

    val leaves by subNode { LeavesConfig(it) }
    val shortGrass by subNode { ShortGrassConfig(it) }
    val connectedGrass by subNode { ConnectedGrassConfig(it) }
    val roundLogs by subNode { RoundLogConfig(it) }
    val cactus by subNode { CactusConfig(it) }
    val lilypad by subNode { LilypadConfig(it) }
    val reed by subNode { ReedConfig(it) }
    val algae by subNode { AlgaeConfig(it) }
    val coral by subNode { CoralConfig(it) }
    val netherrack by subNode { NetherrackConfig(it) }
    val fallingLeaves by subNode { FallingLeavesConfig(it) }
    val risingSoul by subNode { RisingSoulConfig(it) }
}

class LeavesConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node) {
    val enabled by boolean(true, langKey = recurring)
    val snowEnabled by boolean(true)
    val dense by boolean(false)
    val hideInternal by boolean(true)

    val hOffset by double(0.2, min = 0.0, max = 0.4, langKey = recurring)
    val vOffset by double(0.1, min = 0.0, max = 0.4, langKey = recurring)
    val size by double(1.4, min = 0.75, max = 2.5, langKey = recurring)
    val shaderWind by boolean(true, langKey = recurring)
    val saturationThreshold by double(0.1, min = 0.0, max = 1.0, langKey = recurring)
}

class ShortGrassConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node), PopulationConfigData {
    override val enabled by boolean(true, langKey = recurring)
    val myceliumEnabled by boolean(true)
    val snowEnabled by boolean(true)
    val hOffset by double(0.2, min = 0.0, max = 0.4, langKey = recurring)
    val heightMin by double(0.6, min = 0.1, max = 2.5, langKey = recurring)
    val heightMax by double(0.6, min = 0.1, max = 2.5, langKey = recurring) { it.coerceAtLeast(heightMin) }
    val size by double(1.0, min = 0.5, max = 1.5, langKey = recurring)
    override val population by population(64)
    val useGenerated by boolean(false)
    val shaderWind by boolean(true, langKey = recurring)
    val saturationThreshold by double(0.1, min = 0.0, max = 1.0, langKey = recurring)
}

class ConnectedGrassConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node) {
    val enabled by boolean(true, langKey = recurring)
    val snowEnabled by boolean(true)
}

class RoundLogConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node) {
    val enabled by boolean(true, langKey = recurring)

    val defaultY by boolean(false)
    val connectSolids by boolean(false)
    val lenientConnect by boolean(true)
    val connectPerpendicular by boolean(true)
    val connectGrass by boolean(true)

    val radiusSmall by double(0.25, min = 0.0, max = 0.5)
    val radiusLarge by double(0.44, min = 0.0, max = 0.5) { it.coerceAtLeast(radiusSmall) }
    val dimming by double(0.7, min = 0.0, max = 1.0)
    val zProtection by double(0.99, min = 0.9, max = 1.0)
}

class CactusConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node) {
    val enabled by boolean(true, langKey = recurring)
    val size by double(1.3, min = 1.0, max = 2.0, langKey = recurring)
    val sizeVariation by double(0.1, min = 0.0, max = 0.5)
    val hOffset by double(0.1, min = 0.0, max = 0.5, langKey = recurring)
}

class LilypadConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node), PopulationConfigData {
    override val enabled by boolean(true, langKey = recurring)
    val hOffset by double(0.1, min = 0.0, max = 0.25, langKey = recurring)
    override val population by population(16)
    val shaderWind by boolean(true, langKey = recurring)
}

class ReedConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node), PopulationConfigData {
    override val enabled by boolean(true, langKey = recurring)
    val hOffset by double(0.2, min = 0.0, max = 0.4, langKey = recurring)
    val heightMin by double(1.7, min = 1.5, max = 3.0, langKey = recurring)
    val heightMax by double(2.2, min = 1.5, max = 3.0, langKey = recurring) { it.coerceAtLeast(heightMin) }
    override val population by population(32)
    val minBiomeTemp by double(0.4, min = 0.0, max = 2.0)
    val minBiomeRainfall by double(0.4, min = 0.0, max = 1.0)
    val shaderWind by boolean(true, langKey = recurring)
}

class AlgaeConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node), PopulationConfigData {
    override val enabled by boolean(true, langKey = recurring)
    val hOffset by double(0.1, min = 0.0, max = 0.4, langKey = recurring)
    val size by double(1.0, min = 0.5, max = 1.5, langKey = recurring)
    val heightMin by double(0.5, min = 0.1, max = 1.0, langKey = recurring)
    val heightMax by double(0.5, min = 0.1, max = 1.0, langKey = recurring) { it.coerceAtLeast(heightMin) }
    override val population by population(48)
    val shaderWind by boolean(true, langKey = recurring)
}

class CoralConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node), PopulationConfigData {
    override val enabled by boolean(true, langKey = recurring)
    val shallowWater by boolean(false)
    val hOffset by double(0.2, min = 0.0, max = 0.4, langKey = recurring)
    val vOffset by double(0.1, min = 0.0, max = 0.4, langKey = recurring)
    val size by double(0.7, min = 0.5, max = 1.5, langKey = recurring)
    val crustSize by double(1.4, min = 0.5, max = 1.5)
    val chance by integer(32, min = 0, max = 64)
    override val population by population(48)
}

class NetherrackConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node) {
    val enabled by boolean(true, langKey = recurring)
    val hOffset by double(0.2, min = 0.0, max = 0.4, langKey = recurring)
    val size by double(1.0, min = 0.5, max = 1.5, langKey = recurring)
    val heightMin by double(0.6, min = 0.5, max = 1.5, langKey = recurring)
    val heightMax by double(0.8, min = 0.5, max = 1.5, langKey = recurring) { it.coerceAtLeast(heightMin) }
}

class FallingLeavesConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node) {
    val enabled by boolean(true, langKey = recurring)
    val opacityHack by boolean(false)
    val speed by double(0.05, min = 0.01, max = 0.15)
    val windStrength by double(0.5, min = 0.1, max = 2.0)
    val stormStrength by double(0.8, min = 0.1, max = 2.0) { it.coerceAtLeast(windStrength) }
    val size by double(0.75, min = 0.25, max = 1.5)
    val chance by double(0.05, min = 0.001, max = 1.0)
    val perturb by double(0.25, min = 0.01, max = 1.0)
    val lifetime by double(7.5, min = 1.0, max = 15.0)
}

class RisingSoulConfig(node: ConfigTreeBuilder) : DelegatingConfigGroup(node) {
    val enabled by boolean(true, langKey = recurring)
    val chance by double(0.02, min = 0.001, max = 1.0)
    val perturb by double(0.05, min = 0.01, max = 0.25)
    val headSize by double(1.0, min = 0.25, max = 1.5)
    val trailSize by double(0.75, min = 0.25, max = 1.5)
    val opacity by double(0.5, min = 0.05, max = 1.0)
    val sizeDecay by double(0.97, min = 0.5, max = 1.0)
    val opacityDecay by double(0.97, min = 0.5, max = 1.0)
    val lifetime by double(4.0, min = 1.0, max = 15.0)
    val trailLength by integer(48, min = 2, max = 128)
    val trailDensity by integer(3, min = 1, max = 16)
}