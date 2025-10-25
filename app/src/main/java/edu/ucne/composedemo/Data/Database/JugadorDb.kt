package edu.ucne.composedemo.Data.Local.Jugador.Entities.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composedemo.Data.Local.Jugador.Entities.Dao.JugadorDao
import edu.ucne.composedemo.Data.Local.Jugador.Entities.JugadorEntity
import edu.ucne.composedemo.Data.Local.Partidas.Dao.PartidaDao
import edu.ucne.composedemo.Data.Local.Partidas.Entity.PartidaEntity

@Database(
    entities = [
        JugadorEntity::class, PartidaEntity::class
    ],
    version = 2,
    exportSchema = false
)

abstract class JugadorDb: RoomDatabase(){
    abstract fun JugadorDao(): JugadorDao
    abstract fun PartidaDao(): PartidaDao
}