package Functionality.controller;

import Functionality.model.SearchChannel;
import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;

public class ForMapper {

  public final static ObjectMapper mapper;

  static
  {
      mapper = new ObjectMapper() {

      public <T> T readValue(String s, Class<T> aClass) {
          return JSON.parseObject(s, aClass);
      }

      public String writeValue(Object o) {
          return JSON.toJSONString(o);
      }
  };
      Unirest.setObjectMapper(mapper);
  }

  private static final String APIkey = "AIzaSyDCprAXgnUduPxnUIUtrBAj3P2y4G261EQ";

  public static void searchChannel (String ChannelID) throws UnirestException {
      HttpResponse<SearchChannel> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
              .queryString("key", APIkey)
      .queryString("part", "snippet,statistics")
      .queryString("id", ChannelID)
      .asObject(SearchChannel.class);

      for (int i = 0; i < response.getBody().items.length; i++) {
          System.out.println("title: " + response.getBody().items[i].snippet.title);
          System.out.println("data sozdanija: " + response.getBody().items[i].snippet.publishedAt);
          System.out.println("sub count: " + response.getBody().items[i].statistics.subscriberCount);
          System.out.println("video count: " + response.getBody().items[i].statistics.videoCount);
          System.out.println("view count: " + response.getBody().items[i].statistics.viewCount);

          System.out.println("------------------------------------");
      }
  }

    public static void compareChannel (String ChannelID, String ChannelID1) throws UnirestException {
        HttpResponse<SearchChannel> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", APIkey)
                .queryString("part", "snippet,statistics")
                .queryString("id", ChannelID)
                .asObject(SearchChannel.class);

        HttpResponse<SearchChannel> response1 = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", APIkey)
                .queryString("part", "snippet,statistics")
                .queryString("id", ChannelID1)
                .asObject(SearchChannel.class);

        for (int i = 0; i < response.getBody().items.length; i++) {
            System.out.println("title: " + response.getBody().items[i].snippet.title);
            System.out.println("data sozdanija: " + response.getBody().items[i].snippet.publishedAt);
            System.out.println("sub count: " + response.getBody().items[i].statistics.subscriberCount);
            System.out.println("video count: " + response.getBody().items[i].statistics.videoCount);
            System.out.println("view count: " + response.getBody().items[i].statistics.viewCount);
            System.out.println("------------------------------------");
            System.out.println("title: " + response1.getBody().items[i].snippet.title);
            System.out.println("data sozdanija: " + response1.getBody().items[i].snippet.publishedAt);
            System.out.println("sub count: " + response1.getBody().items[i].statistics.subscriberCount);
            System.out.println("video count: " + response1.getBody().items[i].statistics.videoCount);
            System.out.println("view count: " + response1.getBody().items[i].statistics.viewCount);
        }
    }

    public static void Sorting (String [] ChannelID, String sortParameter) throws UnirestException {
        ArrayList<SearchChannel> channels = new ArrayList<SearchChannel>();
        for(int j=0; j<ChannelID.length;j++) {
          HttpResponse<SearchChannel> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                  .queryString("key", APIkey)
                  .queryString("part", "snippet,statistics")
                  .queryString("id", ChannelID[j])
                  .asObject(SearchChannel.class);
          channels.add(response.getBody());
      }

      //channels.sort();


    }
}
