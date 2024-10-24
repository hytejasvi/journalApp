package com.hytejasvi.journalApp.scheduler;

import com.hytejasvi.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class AppCacheScheduler {

    /*class to clear our App Cache every 5 minutes automatically instead of the need to call an API.*/
    @Autowired
    private AppCache appCache;


    @Scheduled(cron = "0 */5 * * * *")
    public void clearAppCache() {
        appCache.init();
    }
}
