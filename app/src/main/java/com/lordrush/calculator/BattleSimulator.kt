package com.lordrush.calculator

import kotlin.math.sqrt

data class Stats(
    var atkInf: Double = 0.0, var defInf: Double = 0.0, var letInf: Double = 0.0, var hpInf: Double = 0.0,
    var atkLan: Double = 0.0, var defLan: Double = 0.0, var effLan: Double = 0.0, var hpLan: Double = 0.0,
    var atkArc: Double = 0.0, var defArc: Double = 0.0, var letArc: Double = 0.0, var hpArc: Double = 0.0,
    var countInf: Int = 0, var countLan: Int = 0, var countArc: Int = 0
)

object BattleSimulator {

    fun simulate(player: Stats, enemy: Stats): String {
        val playerTotal = player.countInf + player.countLan + player.countArc
        val enemyTotal = enemy.countInf + enemy.countLan + enemy.countArc

        if (enemyTotal == 0) return "Aucune troupe ennemie détectée."

        val playerPower = sqrt(playerTotal.toDouble()) * (
            ((1 + player.atkInf/100) * (1 + player.letInf/100)) +
            ((1 + player.atkLan/100) * (1 + player.effLan/100)) +
            ((1 + player.atkArc/100) * (1 + player.letArc/100))
        )

        val enemyDefense = sqrt(enemyTotal.toDouble()) * (
            ((1 + enemy.defInf/100) * (1 + enemy.hpInf/100)) +
            ((1 + enemy.defLan/100) * (1 + enemy.hpLan/100)) +
            ((1 + enemy.defArc/100) * (1 + enemy.hpArc/100))
        )

        val ratio = playerPower / if (enemyDefense == 0.0) 1.0 else enemyDefense

        return if (ratio >= 1.1) {
            "VICTOIRE ESTIMÉE !\nPertes estimées : ~${(playerTotal * 0.08).toInt()} troupes\nTués chez l'ennemi : ~${(enemyTotal * 0.7).toInt()}"
        } else if (ratio >= 0.9) {
            "COMBAT SERRÉ !\nPertes estimées : ~${(playerTotal * 0.25).toInt()} troupes"
        } else {
            "DÉFAITE ESTIMÉE !\nRisque de lourdes pertes. Augmente tes troupes ou rallie des alliés."
        }
    }
}
