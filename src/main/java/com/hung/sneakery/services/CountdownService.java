package com.hung.sneakery.services;

import com.hung.sneakery.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class CountdownService {
    @Autowired
    CityRepository cityRepository;
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static Timer timer = new Timer();

    private static class MyTimeTask extends TimerTask {
        public void run() {
            System.out.println("Running Task");
            System.out.println("Current Time: " + df.format( new Date()));
            timer.cancel();
        }
    }

    public static void executeTask(LocalDateTime localDateTime) {
        System.out.println("Current Time Execute: " + df.format( new Date()));

        //Date and time at which you want to execute
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(date);
        timer.schedule(new MyTimeTask(), date);
    }
}
