package service;

import io.restassured.RestAssured;
import model.News;
import selenium.PravdaScrapper;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    RestAssured.baseURI = "http://localhost:8888";

    PravdaScrapper pravdaScrapper = new PravdaScrapper();
    List<News> news = pravdaScrapper.getAllNews();
    for (News news1 : news)
      RestAssured.given()
          .queryParam("message", news1.toString())
          .post("/kafkaapp/post");
  }
}
