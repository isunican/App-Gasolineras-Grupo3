package es.unican.gasolineras.repository;

import android.content.Context;

import androidx.room.Room;

public class DatabaseFunction {

    private static AppDatabase database;

    public static AppDatabase getDatabase(Context context) {
        if (database == null) {
            // Inicializa la base de datos si aún no lo está
            database = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database-name")
                    .fallbackToDestructiveMigration()  // Opcional: elimina la base de datos si hay un cambio de esquema.
                    .build();
        }
        return database;
    }
}
