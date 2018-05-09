package models;

public class Settings {

    public Boolean useCache;
    public Boolean useTime;
    public String pathToFile;

    public Settings(Boolean cache, Boolean time, String path) {
        useCache = cache;
        useTime = time;
        pathToFile = path;
    }
}
