package com.sparta.jdbcexample.controller;

import com.sparta.jdbcexample.model.Film;

import java.util.ArrayList;
import java.util.List;

public class ThreadLoader {

  private static ArrayList< Thread > threads = new ArrayList<>();

  public static void loadThreads( List< Film > films ) {
    divideList( films );
    startAll();
    joinAll();
  }

  private static void divideList( List< Film > films ) {
    int midIndex = films.size() / 2;
    List< Film > leftArray = new ArrayList<>();
    List< Film > rightArray = new ArrayList<>();

    for ( int i = 0; i < midIndex; i++ ) {
      leftArray.add( films.get( i ) );
    }

    for ( int i = midIndex; i < films.size(); i++ ) {
      rightArray.add( films.get( i ) );
    }

    createThread( leftArray );
    createThread( rightArray );
  }

  private static void createThread( List< Film > filmArray ) {
    threads.add( new Thread(new JDBCWriter( filmArray)) );
  }

  private static void startAll() {
    for ( Thread thread : threads ) {
      thread.start();
    }
  }

  private static void joinAll() {
    for ( Thread thread : threads ) {
      try {
        thread.join();
      } catch ( InterruptedException ie ) {
        ie.printStackTrace();
      }
    }
  }


}
