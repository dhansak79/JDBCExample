package com.sparta.jdbcexample.controller;

import com.sparta.jdbcexample.model.Film;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCWriter implements Runnable {
  private final List< Film > films;
  private final Connection connection;

  public JDBCWriter( List< Film > films ) {
    this.films = films;
    try {
      this.connection = ConnectionFactory.getConnection();
    } catch ( SQLException e ) {
      throw new RuntimeException( e );
    }
  }

  @Override
  public void run() {
    FilmDao filmDao = new FilmDao();
    filmDao.addFilms( films );
  }
}
