package edu.ucne.composedemo.Presentation.Game

sealed class GameOutcome {
    data class Win(val player: String) : GameOutcome()
    data object Draw : GameOutcome()
}

fun detectOutcome(board: List<List<String>>): GameOutcome? {
    val lines = listOf(
        listOf(0 to 0, 0 to 1, 0 to 2),
        listOf(1 to 0, 1 to 1, 1 to 2),
        listOf(2 to 0, 2 to 1, 2 to 2),
        listOf(0 to 0, 1 to 0, 2 to 0),
        listOf(0 to 1, 1 to 1, 2 to 1),
        listOf(0 to 2, 1 to 2, 2 to 2),
        listOf(0 to 0, 1 to 1, 2 to 2),
        listOf(0 to 2, 1 to 1, 2 to 0)
    )
    for (l in lines) {
        val v = l.map { (r, c) -> board[r][c] }
        if (v[0].isNotEmpty() && v.all { it == v[0] }) return GameOutcome.Win(v[0])
    }
    val anyEmpty = board.any { row -> row.any { it.isEmpty() } }
    return if (!anyEmpty) GameOutcome.Draw else null
}
