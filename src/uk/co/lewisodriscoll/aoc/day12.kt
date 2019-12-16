package uk.co.lewisodriscoll.aoc

import kotlin.math.abs

class Moon(val x: Int, val y: Int, val z: Int, val vx: Int, val vy: Int, val vz: Int) {
    constructor(x: Int, y: Int, z: Int) : this(x, y, z, 0, 0, 0)

    private fun getPotentialEnergy(): Int = abs(x) + abs(y) + abs(z)

    private fun getKineticEnergy(): Int = abs(vx) + abs(vy) + abs(vz)

    fun getTotalEnergy(): Int = getPotentialEnergy() * getKineticEnergy()

    override fun toString(): String = "position: ($x, $y, $z), velocity: ($vx, $vy, $vz)"
}

fun calculateVelocity(moon: Moon, other: Moon): Moon {
    var dvx = 0
    var dvy = 0
    var dvz = 0

    if (moon.x > other.x) {
        dvx -= 1
    } else if (moon.x < other.x) {
        dvx += 1
    }

    if (moon.y > other.y) {
        dvy -= 1
    } else if (moon.y < other.y) {
        dvy += 1
    }

    if (moon.z > other.z) {
        dvz -= 1
    } else if (moon.z < other.z) {
        dvz += 1
    }

    return Moon(moon.x, moon.y, moon.z, moon.vx + dvx, moon.vy + dvy, moon.vz + dvz)
}

fun updateVelocity(moon: Moon, others: List<Moon>): Moon {
    var output = moon
    others.forEach { output = calculateVelocity(output, it) }
    return output
}

fun updatePosition(moon: Moon): Moon =
    Moon(moon.x + moon.vx, moon.y + moon.vy, moon.z + moon.vz, moon.vx, moon.vy, moon.vz)

fun step(moons: List<Moon>): List<Moon> = moons
    .map { updateVelocity(it, moons) }
    .map { updatePosition(it) }

fun part1(moons: List<Moon>): Int = generateSequence(moons) { step(it) }
    .take(1001)
    .map{ it.map(Moon::getTotalEnergy).sum() }
    .last()

fun main() {
    val io = Moon(13, -13, -2)
    val europa = Moon(16, 2, -15)
    val ganymede = Moon(7, -18, -12)
    val callisto = Moon(-3, -8, -8)
    val moons = listOf(io, europa, ganymede, callisto)

    val totalEnergy = part1(moons)
    println("Total energy after 1000 steps: $totalEnergy")
}

