package com.practgame.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.logging.Logger;

public final class AppPreferences {
    public static final String PREF_MUSIC_VOLUME = "volume";
    public static final String PREF_MUSIC_ENABLED = "music.enabled";
    public static final String PREF_SOUND_ENABLED = "sound.enabled";
    public static final String PREF_SOUND_VOL = "sound";
    public static final String PREFS_NAME = "b2dpref";
    public static final String PREF_VIBRATION_ENABLED = "vibration.enabled";

    private final Preferences prefsObject = Gdx.app.getPreferences(PREFS_NAME);

    private static final Logger LOGGER  = Logger.getLogger(AppPreferences.class.getName());

   /* protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    } */

    public boolean isSoundEffectsEnabled() {
        return prefsObject.getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        prefsObject.putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        prefsObject.flush();
    }

    public boolean isMusicEnabled() {
        return prefsObject.getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        prefsObject.putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        prefsObject.flush();
    }

    public float getMusicVolume() {
        return prefsObject.getFloat(PREF_MUSIC_VOLUME, 0.8f);
    }

    public void setMusicVolume(float volume) {
        prefsObject.putFloat(PREF_MUSIC_VOLUME, volume);
        prefsObject.flush();
        LOGGER.info("Volume set : " + getMusicVolume());
    }

    public float getSoundVolume() {
        return prefsObject.getFloat(PREF_SOUND_VOL, 0.7f);
    }

    public void setSoundVolume(float volume) {
        prefsObject.putFloat(PREF_SOUND_VOL, volume);
        prefsObject.flush();
    }

    public boolean isVibrationEnabled(){
        return prefsObject.getBoolean(PREF_VIBRATION_ENABLED, true);
    }
    public void setVibrationEnabled(boolean vibrationEnabled) {
        prefsObject.putBoolean(PREF_VIBRATION_ENABLED, vibrationEnabled);
        prefsObject.flush();
    }
}

