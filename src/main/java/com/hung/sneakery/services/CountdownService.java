package com.hung.sneakery.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CountdownService {
  @Scheduled(fixedDelay = 2000)
  public void scheduleFixedDelayTask() throws InterruptedException{
      System.out.println("Task 1 - " + new Date());
  }

}
