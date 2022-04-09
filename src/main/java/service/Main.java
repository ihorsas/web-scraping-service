package service;

import selenium.PravdaScrapper;

public class Main {

  public static void main(String[] args) {
    PravdaScrapper pravdaScrapper = new PravdaScrapper();
    pravdaScrapper.getAllNews();
  }
}
