sealed interface JugadorEvent {
    data class NombresChanged(val v: String) : JugadorEvent
    data class EmailChanged(val v: String) : JugadorEvent
    object Submit : JugadorEvent
    object Sync : JugadorEvent
    data class ApellidosChanged(val v: String) : JugadorEvent
    data class TelefonoChanged(val v: String) : JugadorEvent
    data class PartidasChanged(val v: String) : JugadorEvent
}
