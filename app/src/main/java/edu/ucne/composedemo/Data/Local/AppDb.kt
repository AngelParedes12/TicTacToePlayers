package edu.ucne.composedemo.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composedemo.Data.Local.Dao.JugadorDao
import edu.ucne.composedemo.Data.Local.Entities.JugadorEntity

@Database(entities = [JugadorEntity::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
}
