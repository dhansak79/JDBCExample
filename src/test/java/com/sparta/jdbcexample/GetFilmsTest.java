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

  @Test
  public void insertANewFilm() {
    FilmDao filmDao = new FilmDao();
    int numberOfFilmsBefore = filmDao.getAllFilms().size();

    Film film = new Film( "Daniel goes on holiday", "the worst holiday ever", ( short ) 2022, 1, "R" );
    int rowsUpdated = filmDao.addFilm( film );
    Assertions.assertEquals( 1, rowsUpdated );

    int numberOfFilmsAfter = filmDao.getAllFilms().size();

    Assertions.assertEquals( 1, (numberOfFilmsAfter - numberOfFilmsBefore) );
  }

  @Test
  public void deleteFilms() {
    //Given a film called Daniel goes on holiday
    FilmDao filmDao = new FilmDao();
    Film danielGoesOnHoliday = new Film( "Daniel goes on holiday", "the worst holiday ever", ( short ) 2022, 1, "R" );
    filmDao.addFilm( danielGoesOnHoliday );
    Assertions.assertTrue( filmDao.getFilmsWithTitle( "Daniel goes on holiday" ).size() > 0 );
    //When I delete the film
    filmDao.deleteFilmWithNameMatching( danielGoesOnHoliday );
    //Then the film no longer exists
    Assertions.assertEquals( 0, filmDao.getFilmsWithTitle( "Daniel goes on holiday" ).size() );
  }


}
