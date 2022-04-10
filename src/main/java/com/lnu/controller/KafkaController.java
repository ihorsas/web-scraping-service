package com.lnu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnu.model.News;
import com.lnu.selenium.PravdaScrapper;
import com.lnu.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kafkaapp")
public class KafkaController {

  @Autowired
  Producer producer;

  @PostMapping(value="/message")
  public void sendMessage(@RequestParam("message") String message) {
    producer.publishToTopic(message);
  }

  @PostMapping(value="/news")
  public void startListening() {
    PravdaScrapper pravdaScrapper = new PravdaScrapper();
    List<News> news = pravdaScrapper.getAllNews();
    for (News news1 : news) {
      try {
        producer.publishToTopic((new ObjectMapper().writeValueAsString(news1)));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }
}
