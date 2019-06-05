package com.practgame.game.Utils;

import java.util.HashMap;

// under construction, it is needed for multilanguage support

// actually, this class isn't needed any more
// after learning libgdx i come to conclusion, that for internationalization (i18n) i will use i18n Bundle
// i already has one in assets -> i18n -> resourse Bundle "string"
// at this time other languages are not available, because different fonts are to be created (under construction)


public class Multilanguage {
    private static HashMap < String, HashMap <String, String> > data;

    private static String language;
    // static initialization block
    static {
        data = new HashMap<String, HashMap<String, String>>();
        loadData();
    }

    private static void loadData(){
                loadEng();
                loadRus();
    }

    private static void loadEng(){
        data.put("eng", new HashMap<String, String>());

        //adding string here
        //UI

        data.get("eng").put("playButton", "Play");
        data.get("eng").put("quitButton", "Exit");
        data.get("eng").put("settingsButton", "Settings");
        data.get("eng").put("demoButton", "Demo Level");

    }

    private static void loadRus(){
        data.put("rus", new HashMap<String, String>());

        //adding string here
        //UI

        data.get("rus").put("playButton", "Играть");
        data.get("rus").put("quitButton", "Выход");
        data.get("rus").put("settingsButton", "Настройки");
        data.get("rus").put("demoButton", "Demo уровень");

    }

    public static void setLanguage(String lang){
        language =  lang;
    }

    public static String getData(String key){ // maybe you should overload it
        return data.get(language).get(key);
    }

    public static String getLanguage() {
        return language;
    }
}
