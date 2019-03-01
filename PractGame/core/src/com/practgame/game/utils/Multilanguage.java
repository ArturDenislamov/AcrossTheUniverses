package com.practgame.game.utils;


import java.util.HashMap;

public class Multilanguage {
    // 02/03 under construction, it is needed for multilanguage support
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
