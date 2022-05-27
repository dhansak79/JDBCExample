package com.sparta.jdbcexample.controller;

import com.sparta.jdbcexample.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDao {

  public List< Film > getFilmsWithRating( String rating ) {
    String sqlStatement = "SELECT * FROM sakila.film WHERE rating = ?";
    String[] arguments = new String[]{ rating };
    return getFilms( sqlStatement, arguments );
  }

  public List< Film > getFilmsWithTitle( String title ) {
    String sqlStatement = "SELECT * FROM sakila.film WHERE title = ?";
    String[] arguments = new String[]{ title };
    return getFilms( sqlStatement, arguments );
  }

  public List< Film > getAllFilms() {
    String sqlStatement = "SELECT * FROM sakila.film";
    return getFilms( sqlStatement );
  }

  public List< Film > getFilmsWithRatingAndDescription( String rating, String description ) {
    String sqlStatement =
            "SELECT * FROM sakila.film WHERE rating = ? AND description LIKE ?";
    String[] arguments = new String[]{ rating, "%" + description + "%" };
    return getFilms( sqlStatement, arguments );
  }

  public int addFilm( Film film ) {
    String sqlStatement =
            "INSERT INTO film (title, description, release_year, language_id, rating) VALUES (?, ?, ?, ?, ?)";
    Object[] arguments = new Object[]{ film.getTitle(),
            film.getDescription(), film.getReleaseYear(), film.getLanguageId(), film.getRating() };
    return DbUtils.executeUpdate( sqlStatement, arguments );
  }

  public int deleteFilmWithNameMatching( Film film ) {
    String sqlStatement = "DELETE FROM film WHERE title = ?";
    Object[] arguments = new Object[]{ film.getTitle() };
    return DbUtils.executeUpdate( sqlStatement, arguments );
  }

  private List< Film > getFilms( String sqlStatement ) {
    return getFilms( sqlStatement, new String[]{} );
  }

  private List< Film > getFilms( String sqlStatement, String[] arguments ) {
    List< Film > films;
    ResultSet resultSet = DbUtils.executeQuery( sqlStatement, arguments );
    films = getFilmsFromResultSet( resultSet );
    DbUtils.closeTheConnection();
    return films;
  }

  private List< Film > getFilmsFromResultSet( ResultSet resultSet ) {
    List< Film > films = new ArrayList<>();
    try {
      while ( resultSet.next() ) {
        Film film = new Film(
                resultSet.getString( "title" ),
                resultSet.getString( "description" ),
                resultSet.getShort( "release_year" ),
                resultSet.getInt( "language_id" ),
                resultSet.getString( "rating" ) );
        films.add( film );
      }
    } catch ( SQLException e ) {
      e.printStackTrace();
    }
    return films;
  }
}