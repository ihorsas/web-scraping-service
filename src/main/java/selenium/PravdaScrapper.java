package selenium;

import static selenium.DriverManager.getDriver;

import model.News;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PravdaScrapper {
  private final String ROOT_URL = "https://www.pravda.com.ua";
  private final String NEWS_URL = ROOT_URL + "/news/";

  public List<News> getAllNews() {
    try {
      DriverManager.getDriver().get(NEWS_URL);
      List<String> linksToNews = getLinks();
      return getNews(linksToNews.subList(0, 5));
    } finally {
      DriverManager.quit();
    }
  }

  private List<News> getNews(List<String> linksToNews) {
    List<News> news = new ArrayList<>();
    for(String link : linksToNews) {
      getDriver().navigate().to(link);
      String title = getDriver().findElement(By.cssSelector(".post_title")).getText();
      String timeAndAuthor = getDriver().findElement(By.cssSelector(".post_time")).getText();
      Integer views = Integer
          .valueOf(getDriver().findElement(By.cssSelector(".post_views")).getText().trim());
      String author = getAuthor(timeAndAuthor);
      Date date = getDate(getStringDate(timeAndAuthor));
      news.add(News.builder()
          .title(title)
          .author(author)
          .views(views)
          .publishDate(date)
          .build());
    }
    return news;
  }

  private String getAuthor(String timeAndAuthor) {
    String[] separatedTimeAuthor = timeAndAuthor.split(" — ");
    if(separatedTimeAuthor.length == 1) {
      return null;
    }
    return separatedTimeAuthor[0];
  }

  private String getStringDate(String timeAndAuthor) {
    String[] separatedTimeAuthor = timeAndAuthor.split(" — ");
    if(separatedTimeAuthor.length == 1) {
      return separatedTimeAuthor[0];
    }
    return separatedTimeAuthor[1];
  }

  private Date getDate(String stringDate) {
    // example input: Субота, 9 квітня 2022, 12:45
    String[] dateTime = stringDate.split(", ");
    String[] separatedDate = dateTime[1].split(" ");
    int day = Integer.parseInt(separatedDate[0]);
    int month = Integer.parseInt(getMonth(separatedDate[1]));
    int year = Integer.parseInt(separatedDate[2]);
    String[] separatedtime = dateTime[2].split(":");
    int hours = Integer.parseInt(separatedtime[0]);
    int minutes = Integer.parseInt(separatedtime[1]);
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, day, hours, minutes);
    return calendar.getTime();
  }

  private String getMonth(String ukrainianMonth) {
    switch (ukrainianMonth.toLowerCase()) {
      case "січня": return "01";
      case "лютого": return "02";
      case "березня": return "03";
      case "квітня": return "04";
      case "травня": return "05";
      case "червня": return "06";
      case "липня": return "07";
      case "серпня": return "08";
      case "вересня": return "09";
      case "жовтня": return "10";
      case "листопада": return "11";
      case "грудня": return "12";
      default: throw new RuntimeException("Can't recognize month: " + ukrainianMonth);
    }
  }

  private List<String> getLinks() {
    return getDriver()
        .findElements(By.cssSelector(".article_header>a"))
        .stream()
        .map(element -> element.getAttribute("href"))
        .filter(link -> link.matches(ROOT_URL + "\\/news\\/\\d{4}\\/.*"))
        .collect(Collectors.toList());
  }
}
