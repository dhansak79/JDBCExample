package com.sparta.jdbcexample.model;

public class Film {
  private final String title;
  private final String description;

  private final short releaseYear;
  private final int languageId;

  private final String rating;

  public Film( final String title, final String description, final short releaseYear,
               final int languageId, final String rating ) {
    this.title = title;
    this.description = description;
    this.releaseYear = releaseYear;
    this.languageId = languageId;
    this.rating = rating;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public short getReleaseYear() {
    return releaseYear;
  }

  public int getLanguageId() {
    return languageId;
  }

  public String getRating() {
    return rating;
  }

  @Override
  public String toString() {
    return "Film{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", releaseYear=" + releaseYear +
            ", languageId=" + languageId +
            ", rating='" + rating + '\'' +
            '}';
  }
}
