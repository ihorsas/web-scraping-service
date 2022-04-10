package com.lnu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {
  private String title;
  private String author;
  private Integer views;
  private Date publishDate;
}
