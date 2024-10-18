package com.hytejasvi.journalApp.cache;

import com.hytejasvi.journalApp.Entity.ConfigJournalAppEntity;
import com.hytejasvi.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class acts as an in-memory cache.
 * Once the application starts, the class is initialized, and after the bean is created,
 * the @PostConstruct method is called.
 * In this method, we call the repository to retrieve all entries from the collection: config_journal_app.
 * The retrieved data is then stored in the App_Cache map.
 * Further, when we need any configuration information, instead of querying the database again,
 * we can just access AppCache.App_Cache to retrieve the cached data.
 */
@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }

    public Map<String, String> App_Cache;

    @Autowired
    ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct
    public void init() {
        App_Cache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all) {
            App_Cache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
