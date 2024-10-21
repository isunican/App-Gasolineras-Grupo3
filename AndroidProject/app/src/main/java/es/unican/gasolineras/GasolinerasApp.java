package es.unican.gasolineras;

import android.app.Application;

import androidx.room.Room;

import dagger.hilt.android.HiltAndroidApp;
import es.unican.gasolineras.repository.AppDatabase;
import lombok.Getter;

/**
 * The Application class must be explicitly annotated for the dependency injection to work
 */

@HiltAndroidApp
public class GasolinerasApp extends Application {


}
