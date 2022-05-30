package com.sparta.jdbcexample.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtils {
  public static PreparedStatement getPreparedStatement( String sqlStatement, Object[] arguments ) {
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

  public static int executeUpdate( String sqlStatement, Object[] arguments ) {
    int rowsAffected = 0;
    PreparedStatement preparedStatement = getPreparedStatement( sqlStatement, arguments );
    try {
      rowsAffected = preparedStatement.executeUpdate();
    } catch ( SQLException e ) {
      e.printStackTrace();
    }
    //TODO add logging properly
//    System.out.println( rowsAffected + " rows were affected." );
    return rowsAffected;
  }

  public static ResultSet executeQuery( String sqlStatement, String[] arguments ) {
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = getPreparedStatement( sqlStatement, arguments );
    try {
      resultSet = preparedStatement.executeQuery();
    } catch ( SQLException e ) {
      e.printStackTrace();
    }
    return resultSet;
  }

  public static void closeTheConnection() {
    try {
      ConnectionFactory.closeConnection();
    } catch ( SQLException e ) {
      e.printStackTrace();
    }
  }
}
