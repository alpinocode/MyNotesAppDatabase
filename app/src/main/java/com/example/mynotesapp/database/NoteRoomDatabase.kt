package com.example.mynotesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// ketika schema di update versionnya juga harus di update
@Database(entities = [Note::class], version = 1)
abstract class NoteRoomDatabase :RoomDatabase() {
    abstract fun noteDao():NoteDao

    companion object {
//        mencegah beberapa contoh pembukaan basis data pada waktu yang sama
        @Volatile
        private var INSTANCE:NoteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context):NoteRoomDatabase{
            // jika database nya belum ada maka buatlah databasenya
            if (INSTANCE == null) {
                synchronized(NoteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteRoomDatabase::class.java, "note_database")
//                         jika tidak ingin to provide migrations dan ingin secara khusus database tidak di hapus
//                        ketika mengupgrade the version
//                        .fallbackToDestructiveMigrationFrom()
                        .build()
                }
            }
            return INSTANCE as NoteRoomDatabase
        }
    }
}