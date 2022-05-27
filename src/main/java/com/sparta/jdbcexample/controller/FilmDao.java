package com.sparta.jdbcexample.controller;

import com.sparta.jdbcexample.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDao {

  public List< Film > getFilmsWithRating( String rating ) {
    String sqlStatement = "SELECT * FROM sakila.film WHERE rating = ?";
    String[] arguments = new String[]{ rating };
    return executeQuery( sqlStatement, arguments );
  }

  public List< Film > getFilmsWithTitle( String title ) {
    String sqlStatement = "SELECT * FROM sakila.film WHERE title = ?";
    String[] arguments = new String[]{ title };
    return executeQuery( sqlStatement, arguments );
  }

  public List< Film > getAllFilms() {
    String sqlStatement = "SELECT * FROM sakila.film";
    return executeQuery( sqlStatement );
  }

  public List< Film > getFilmsWithRatingAndDescription( String rating, String description ) {
    String sqlStatement = "SELECT * FROM sakila.film WHERE rating = ? AND description LIKE ?";
    String[] arguments = new String[]{ rating, "%" + description + "%" };
    return executeQuery( sqlStatement, arguments );
  }

  public int addFilm( Film film ) {
    String sqlStatement = "INSERT INTO film (title, description, release_year, language_id, rating) VALUES (?, ?, ?, ?, ?)";
    Object[] arguments = new Object[]{ film.getTitle(),
            film.getDescription(), film.getReleaseYear(), film.getLanguageId(), film.getRating() };
    return executeUpdate( sqlStatement, arguments );
  }

  public int deleteFilmWithNameMatching( Film film ) {
    String sqlStatement = "DELETE FROM film WHERE title = ?";
    String[] arguments = new String[]{ film.getTitle() };
    return executeUpdate( sqlStatement, arguments );
  }

  private List< Film > executeQuery( String sqlStatement ) {
    return executeQuery( sqlStatement, new String[]{} );
  }

  private List< Film > executeQuery( String sqlStatement, String[] arguments ) {
    List< Film > films;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = getPreparedStatement( sqlStatement, arguments );
    try {
      resultSet = preparedStatement.executeQuery();
    } catch ( SQLException e ) {
      e.printStackTrace();
    } finally {
      films = getFilmsFromResultSet( resultSet );
      closeTheConnection();
    }
    return films;
  }

  private void closeTheConnection() {
    try {
      ConnectionFactory.closeConnection();
    } catch ( SQLException e ) {
      e.printStackTrace();
    }
  }

  private int executeUpdate( String sqlStatement, Object[] arguments ) {
    int rowsAffected = 0;
    PreparedStatement preparedStatement = getPreparedStatement( sqlStatement, arguments );
    try {
      preparedStatement.executeUpdate();
    } catch ( SQLException e ) {
      e.printStackTrace();
    }
    //TODO add logging properly
    System.out.println( rowsAffected + " rows were affected." );
    return rowsAffected;
  }

  private PreparedStatement getPreparedStatement( String sqlStatement, Object[] arguments ) {
    PreparedStatement preparedStatement = null;
    try {
      Connection connection = ConnectionFactory.getConnection();
      preparedStatement = connection.prepareStatement( sqlStatement );
      int i = 1;
      for ( Object argument : arguments ) {
        preparedStatement.setObject( i, argument );
        i++;
      }
    } catch ( SQLException e ) {
      e.printStackTrace();
    }
    return preparedStatement;
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
        films.add( film );
      }
    } catch ( SQLException e ) {
      e.printStackTrace();
    }
    return films;
  }
}