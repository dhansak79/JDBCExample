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
    int expectedTotalNumberOfFilms = filmDao.getAllFilms().size() + 1;
    int expectedNumberOfFilmsCalledDanielGoesOnHoliday = filmDao.getFilmsWithTitle( "Daniel goes on holiday" ).size() + 1;
    Film film = new Film( "Daniel goes on holiday", "the worst holiday ever", ( short ) 2022, 1, "R" );
    filmDao.addFilm( film );
    Assertions.assertEquals( expectedTotalNumberOfFilms, filmDao.getAllFilms().size() );
    Assertions.assertEquals( expectedNumberOfFilmsCalledDanielGoesOnHoliday, filmDao.getFilmsWithTitle( "Daniel goes on holiday" ).size() );
    System.out.println( filmDao.getFilmsWithTitle( "Daniel goes on holiday" ) );
  }

  @Test
  public void deleteFilms() {
    FilmDao filmDao = new FilmDao();
    Film danielGoesOnHoliday = new Film( "Daniel goes on holiday", "the worst holiday ever", ( short ) 2022, 1, "R" );
    filmDao.addFilm( danielGoesOnHoliday );
    Assertions.assertTrue( ( filmDao.getFilmsWithTitle( "Daniel goes on holiday" ).size() ) > 0 );
    filmDao.deleteFilmWithNameMatching( danielGoesOnHoliday );
    Assertions.assertEquals( 0, filmDao.getFilmsWithTitle( "Daniel goes on holiday" ).size() );
  }


}
