package com.sparta.jdbcexample;

import com.sparta.jdbcexample.controller.FilmDao;
import com.sparta.jdbcexample.model.Film;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GetFilmsTest {

  @Test
  public void getAllFilms() {
    FilmDao filmDao = new FilmDao();
    List< Film > films = filmDao.getAllFilms();
    Assertions.assertEquals( 1000, films.size() );
    Assertions.assertEquals( "ACADEMY DINOSAUR", films.get( 0 ).getTitle() );
    System.out.println( films.get( 0 ) );
  }

  @Test
  public void getAllFilmsWithRatingPg() {
    FilmDao filmDao = new FilmDao();
    List< Film > films = filmDao.getFilmsWithRating( "PG" );
    Assertions.assertEquals( 194, films.size() );
    System.out.println( films.get( 0 ) );
  }

  @Test
  public void getAllFilmsWithRatingPgAndDescriptionContains() {
    FilmDao filmDao = new FilmDao();
    List< Film > films = filmDao.getFilmsWithRatingAndDescription( "PG", "Secret Agent" );
    Assertions.assertEquals( 8, films.size() );
    System.out.println( films.get( 0 ) );
  }


}
