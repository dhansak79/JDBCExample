package com.sparta.jdbcexample;


import com.sparta.jdbcexample.controller.FilmDao;
import com.sparta.jdbcexample.model.Film;

import java.util.List;

public class JDBCMain {

  public static void main( String[] args ) {
    FilmDao filmDao = new FilmDao();
    List< Film > films = filmDao.getAllFilms();
    for ( Film film : films ) {
      System.out.println( film );
    }
  }
}