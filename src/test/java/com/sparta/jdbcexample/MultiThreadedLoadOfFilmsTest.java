package com.sparta.jdbcexample;

import com.sparta.jdbcexample.controller.ThreadLoader;
import com.sparta.jdbcexample.model.Film;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadedLoadOfFilmsTest {

  @Test
  public void loadLargeArrayOfFilms() {
    List< Film > films = new ArrayList<>();
    for ( int i = 0; i < 1000; i++ ) {
      films.add( new Film( "film number " + i, "description",
              ( short ) 2022, 1, "PG-13" ) );
    }

    long start = System.nanoTime();
    ThreadLoader.loadThreads( films );
    long end = System.nanoTime();
    System.out.println( "Took " + ( end - start ) + " nanoseconds to load " + films.size() +  " films." );
  }

}
