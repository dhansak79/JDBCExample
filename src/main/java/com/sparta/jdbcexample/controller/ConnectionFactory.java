package com.sparta.jdbcexample.controller;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

  private static Connection connection = null;

  //can't instantiate the class
  private ConnectionFactory() {
  }

  public static Connection getConnection() throws IOException, SQLException {
    if ( connection == null ) {
      Properties databaseProperties = new Properties();
      databaseProperties.load( new FileReader( "src/main/resources/mysql.properties" ) );
      connection = DriverManager.getConnection(
              databaseProperties.getProperty( "dburl" ),
              databaseProperties.getProperty( "dbuserid" ),
              databaseProperties.getProperty( "dbpassword" ) );
    }
    return connection;
  }

  public static void closeConnection() throws SQLException {
    if ( connection != null ) {
      connection.close();
      connection = null;
    }
  }
}