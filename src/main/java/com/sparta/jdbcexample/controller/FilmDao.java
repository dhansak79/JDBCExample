package com.sparta.jdbcexample.controller;

import com.sparta.jdbcexample.model.Film;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDao {

  public List< Film > getFilmsWithRating( String rating ) {
    String sqlStatement = "select * from sakila.film WHERE rating = ?";
    String[] arguments = new String[]{ rating };
    return executeQuery( sqlStatement, arguments );
  }

  public List< Film > getFilmsWithTitle( String title ) {
    String sqlStatement = "select * from sakila.film WHERE title = ?";
    String[] arguments = new String[]{ title };
    return executeQuery( sqlStatement, arguments );
  }

  public List< Film > getAllFilms() {
    String sqlStatement = "select * from sakila.film";
    return executeQuery( sqlStatement );
  }

  public List< Film > getFilmsWithRatingAndDescription( String rating, String description ) {
    String sqlStatement = "select * from sakila.film WHERE rating = ? AND description LIKE ?";
    String[] arguments = new String[]{ rating, "%" + description + "%" };
    return executeQuery( sqlStatement, arguments );
  }

  public int addFilm( Film film ) {
    String sqlStatement = "INSERT INTO film (title, description, release_year, language_id, rating) VALUES (?, ?, ?, ?, ?)";
    Object[] arguments = new Object[]{ film.getTitle(), film.getDescription(), film.getReleaseYear(), film.getLanguageId(), film.getRating() };
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
    List< Film > films = new ArrayList<>();
    try {
      Connection connection = ConnectionFactory.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement( sqlStatement );
      int i = 1;
      for ( String argument : arguments ) {
        preparedStatement.setString( i, argument );
        i++;
      }
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();
      List< Film > films1 = new ArrayList<>();
      while ( resultSet.next() ) {
        Film film = new Film(
                resultSet.getString( "title" ),
                resultSet.getString( "description" ),
                resultSet.getShort( "release_year" ),
                resultSet.getInt( "language_id" ),
                resultSet.getString( "rating" ) );

        films1.add( film );
      }
      films = films1;
    } catch ( IOException | SQLException e ) {
      e.printStackTrace();
    } finally {
      try {
        ConnectionFactory.closeConnection();
      } catch ( SQLException e ) {
        e.printStackTrace();
      }
    }
    return films;
  }

  private int executeUpdate( String sqlStatement, Object[] arguments ) {
    int rowsAffected = 0;
    try {
      Connection connection = ConnectionFactory.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement( sqlStatement );
      int i = 1;
      for ( Object argument : arguments ) {
        preparedStatement.setObject( i, argument );
        i++;
      }
      rowsAffected = preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch ( IOException | SQLException e ) {
      e.printStackTrace();
      return rowsAffected;
    } finally {
      try {
        ConnectionFactory.closeConnection();
      } catch ( SQLException e ) {
        e.printStackTrace();
      }
    }
    return rowsAffected;
  }

}

