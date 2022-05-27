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

  public List< Film > getAllFilms() {
    String sqlStatement = "select * from sakila.film";
    return getFilms( sqlStatement );
  }

  public List< Film > getFilmsWithRatingAndDescription( String rating, String description ) {
    String sqlStatement = "select * from sakila.film WHERE rating = ? AND description LIKE ?";
    String[] arguments = new String[]{ rating, "%" + description + "%" };
    return getFilms(sqlStatement, arguments);
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

