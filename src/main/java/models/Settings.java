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

    public Settings() {
    }

    public Boolean getUseCache() {
        return useCache;
    }

    public void setUseCache(Boolean useCache) {
        this.useCache = useCache;
    }

    public Boolean getUseTime() {
        return useTime;
    }

    public void setUseTime(Boolean useTime) {
        this.useTime = useTime;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
}
