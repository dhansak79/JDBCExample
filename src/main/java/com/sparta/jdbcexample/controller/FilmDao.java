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
    return getFilms( sqlStatement, arguments );
  }

  public List< Film > getFilmsWithTitle( String title ) {
    String sqlStatement = "select * from sakila.film WHERE title = ?";
    String[] arguments = new String[]{ title };
    return getFilms( sqlStatement, arguments );
  }

  public List< Film > getAllFilms() {
    String sqlStatement = "select * from sakila.film";
    return getFilms( sqlStatement );
  }

  public List< Film > getFilmsWithRatingAndDescription( String rating, String description ) {
    String sqlStatement = "select * from sakila.film WHERE rating = ? AND description LIKE ?";
    String[] arguments = new String[]{ rating, "%" + description + "%" };
    return getFilms( sqlStatement, arguments );
  }

  public int addFilm( Film film ) {
    String sqlStatement = "INSERT INTO film (title, description, release_year, language_id, rating) VALUES (?, ?, ?, ?, ?)";
    int rowsAffected = 0;
    try {
      Connection connection = ConnectionFactory.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement( sqlStatement );
      preparedStatement.setString( 1, film.getTitle() );
      preparedStatement.setString( 2, film.getDescription() );
      preparedStatement.setShort( 3, film.getReleaseYear() );
      preparedStatement.setInt( 4, film.getLanguageId() );
      preparedStatement.setString( 5, film.getRating() );
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

  public int deleteFilmWithName( String nameOfFilmToDelete ) {
    String sqlStatement = "DELETE FROM film WHERE title = ?";
    int rowsAffected = 0;
    try {
      Connection connection = ConnectionFactory.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement( sqlStatement );
      preparedStatement.setString( 1, nameOfFilmToDelete );
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

  private List< Film > getFilms( String sqlStatement ) {
    return getFilms( sqlStatement, new String[]{} );
  }

  private List< Film > getFilms( String sqlStatement, String[] arguments ) {
    List< Film > films = new ArrayList<>();
    try {
      Connection connection = ConnectionFactory.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement( sqlStatement );
      int i = 1;
      for ( String argument : arguments ) {
        preparedStatement.setString( i, argument );
        i++;
      }
      films = executeQuery( preparedStatement );
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

  private List< Film > executeQuery( PreparedStatement statement ) throws SQLException {
    ResultSet resultSet;
    resultSet = statement.executeQuery();
    List< Film > films = new ArrayList<>();
    while ( resultSet.next() ) {
      Film film = new Film(
              resultSet.getString( "title" ),
              resultSet.getString( "description" ),
              resultSet.getShort( "release_year" ),
              resultSet.getInt( "language_id" ),
              resultSet.getString( "rating" ) );

      films.add( film );
    }
    return films;
  }
}

