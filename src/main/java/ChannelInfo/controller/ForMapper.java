package ChannelInfo.controller;

import ChannelInfo.enums.SortKey;
import ChannelInfo.model.SearchChannel;
import CommentInfo.model.CommentModel.SearchCountComment;
import CommentInfo.model.SearchComment;
import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.*;
import java.util.concurrent.*;

public class ForMapper {

  public final static ObjectMapper mapper;
  public static ExecutorService executorService;

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
          System.out.println("Название: " + response.getBody().items[i].snippet.title);
          System.out.println("Дата создания: " + response.getBody().items[i].snippet.publishedAt);
          System.out.println("Количество подписчиков: " + response.getBody().items[i].statistics.subscriberCount);
          System.out.println("Количество видео: " + response.getBody().items[i].statistics.videoCount);
          System.out.println("Количество просмотров: " + response.getBody().items[i].statistics.viewCount);

          System.out.println("------------------------------------");
      }
  }

  public static Callable<SearchChannel> GetResponse (String id)
  {
      Callable<SearchChannel> response =() -> {
          HttpResponse<SearchChannel> r = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                  .queryString("key", APIkey)
                  .queryString("part", "snippet,statistics")
                  .queryString("id", id)
                  .asObject(SearchChannel.class);
          return r.getBody();
      };
      return response;
  }


    public static void compareChannel (String ChannelID, String ChannelID1) throws UnirestException, ExecutionException, InterruptedException {

        executorService = Executors.newFixedThreadPool(2);
        Future<SearchChannel> future = executorService.submit(GetResponse(ChannelID));
        Future<SearchChannel> future1 = executorService.submit(GetResponse(ChannelID1));

        for (int i = 0; i < future.get().items.length; i++) {
            System.out.println("Название: " + future.get().items[i].snippet.title);
            System.out.println("Дата создания: " + future.get().items[i].snippet.publishedAt);
            System.out.println("Количество подписчиков: " + future.get().items[i].statistics.subscriberCount);
            System.out.println("Количество видео: " + future.get().items[i].statistics.videoCount);
            System.out.println("Количество просмотров: " + future.get().items[i].statistics.viewCount);
            System.out.println("------------------------------------");
            System.out.println("Название: " + future1.get().items[i].snippet.title);
            System.out.println("Дата создания: " + future1.get().items[i].snippet.publishedAt);
            System.out.println("Количество подписчиков: " + future1.get().items[i].statistics.subscriberCount);
            System.out.println("Количество видео: " + future1.get().items[i].statistics.videoCount);
            System.out.println("Количество просмотров: " + future1.get().items[i].statistics.viewCount);
        }
        executorService.shutdown();
    }

    public static void Sorting (String [] ChannelID, SortKey key) throws UnirestException {

      ArrayList<SearchChannel> channels = new ArrayList<SearchChannel>();

        for(int j=0; j<ChannelID.length;j++) {
          HttpResponse<SearchChannel> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                  .queryString("key", APIkey)
                  .queryString("part", "snippet,statistics")
                  .queryString("id", ChannelID[j])
                  .asObject(SearchChannel.class);
          channels.add(response.getBody());
      }

      switch (key) {
          case NAME:
              Collections.sort(channels, SearchChannel.NameComparator);
              break;
          case DATE:
              Collections.sort(channels, SearchChannel.DateComparator);
              break;
          case SUBSCRIBERS:
              Collections.sort(channels, SearchChannel.SubsComparator);
              break;
          case VIDEOS:
              Collections.sort(channels, SearchChannel.VideosComparator);
              break;
          case VIEWS:
              Collections.sort(channels, SearchChannel.ViewsComparator);
              break;
              default:
                  System.out.println("Error !");
                  return;
      }

        for(SearchChannel a : channels) {
            System.out.println("Название: " + a.items[0].snippet.title);
            System.out.println("Дата создания: " + a.items[0].snippet.publishedAt);
            System.out.println("Количество подписчиков: " + a.items[0].statistics.subscriberCount);
            System.out.println("Количество видео: " + a.items[0].statistics.videoCount);
            System.out.println("Количество просмотров: " + a.items[0].statistics.viewCount);
            System.out.println("------------------------------------");
        }
    }


    public static int GetCommentCount (String ChannelID) throws UnirestException {
        HttpResponse<SearchComment> response = Unirest.get("https://www.googleapis.com/youtube/v3/search")
                .queryString("key", APIkey)
                .queryString("part", "snippet")
                .queryString("maxResults", 50)
                .queryString("channelId", ChannelID)
                .asObject(SearchComment.class);

        int totalCommentCount = GetCountComment(response.getBody());
        String nextToken = response.getBody().nextPageToken;

        while (nextToken != null) {
            HttpResponse<SearchComment> response1 = null;
            response1 = Unirest.get("https://www.googleapis.com/youtube/v3/search")
                    .queryString("key", APIkey)
                    .queryString("part", "snippet")
                    .queryString("maxResults", 50)
                    .queryString("channelId", ChannelID)
                    .queryString("pageToken", nextToken)
                    .asObject(SearchComment.class);

            totalCommentCount += GetCountComment(response1.getBody());
            nextToken =response1.getBody().nextPageToken;
        }
        return totalCommentCount;
    }

    public static int GetCountComment(SearchComment array) {
        int totalCommentCount = 0;
        for (int i = 0; i < array.items.length; i++) {
            if(array.items[i].id.videoId == null)
                continue;
            totalCommentCount += GetVideoComment(array.items[i].id.videoId);
        }
        return totalCommentCount;
    }
    public static int GetVideoComment(String videoId) {
        HttpResponse<SearchCountComment> response = null;
        try {
            response = Unirest.get("https://www.googleapis.com/youtube/v3/videos")
                    .queryString("key", APIkey)
                    .queryString("part", "statistics")
                    .queryString("id", videoId)
                    .asObject(SearchCountComment.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(response.getBody().items[0].statistics.commentCount);
    }


    public static void SearchChannelComment (String ChannelID) throws UnirestException {
        HttpResponse<SearchChannel> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", APIkey)
                .queryString("part", "snippet,statistics")
                .queryString("id", ChannelID)
                .asObject(SearchChannel.class);

        for (int i = 0; i < response.getBody().items.length; i++) {
            System.out.println("Название: " + response.getBody().items[i].snippet.title);
            System.out.println("Дата создания: " + response.getBody().items[i].snippet.publishedAt);
            System.out.println("Количество подписчиков: " + response.getBody().items[i].statistics.subscriberCount);
            System.out.println("Количество видео: " + response.getBody().items[i].statistics.videoCount);
            System.out.println("Количество просмотров: " + response.getBody().items[i].statistics.viewCount);
            System.out.println("Количество комментариев: " + GetCommentCount(ChannelID) );

            System.out.println("------------------------------------");
        }
    }

    public static void compareChannelComment (String ChannelID, String ChannelID1) throws UnirestException {
        HttpResponse<SearchChannel> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", APIkey)
                .queryString("part", "snippet,statistics")
                .queryString("id", ChannelID)
                .asObject(SearchChannel.class);

        for (int i = 0; i < response.getBody().items.length; i++) {
            System.out.println("Название: " + response.getBody().items[i].snippet.title);
            System.out.println("Дата создания: " + response.getBody().items[i].snippet.publishedAt);
            System.out.println("Количество подписчиков: " + response.getBody().items[i].statistics.subscriberCount);
            System.out.println("Количество видео: " + response.getBody().items[i].statistics.videoCount);
            System.out.println("Количество просмотров: " + response.getBody().items[i].statistics.viewCount);
            System.out.println("Количество комментариев: " + GetCommentCount(ChannelID) );

            System.out.println("------------------------------------");
        }

        HttpResponse<SearchChannel> response1 = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", APIkey)
                .queryString("part", "snippet,statistics")
                .queryString("id", ChannelID1)
                .asObject(SearchChannel.class);

        for (int i = 0; i < response.getBody().items.length; i++) {
            System.out.println("Название: " + response1.getBody().items[i].snippet.title);
            System.out.println("Дата создания: " + response1.getBody().items[i].snippet.publishedAt);
            System.out.println("Количество подписчиков: " + response1.getBody().items[i].statistics.subscriberCount);
            System.out.println("Количество видео: " + response1.getBody().items[i].statistics.videoCount);
            System.out.println("Количество просмотров: " + response1.getBody().items[i].statistics.viewCount);
            System.out.println("Количество комментариев: " + GetCommentCount(ChannelID1) );

            System.out.println("------------------------------------");
        }
    }


    public static void SortingWithComment (String [] ChannelID, SortKey key) throws UnirestException {

        ArrayList<SearchChannel> channels = new ArrayList<SearchChannel>();

        for (int j = 0; j < ChannelID.length; j++) {
            HttpResponse<SearchChannel> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                    .queryString("key", APIkey)
                    .queryString("part", "snippet,statistics")
                    .queryString("id", ChannelID[j])
                    .asObject(SearchChannel.class);
            SearchChannel channel = response.getBody();
            channel.commentCount = GetCommentCount(ChannelID[j]);
            channels.add(channel);
        }

        switch (key) {
            case NAME:
                Collections.sort(channels, SearchChannel.NameComparator);
                break;
            case DATE:
                Collections.sort(channels, SearchChannel.DateComparator);
                break;
            case SUBSCRIBERS:
                Collections.sort(channels, SearchChannel.SubsComparator);
                break;
            case VIDEOS:
                Collections.sort(channels, SearchChannel.VideosComparator);
                break;
            case VIEWS:
                Collections.sort(channels, SearchChannel.ViewsComparator);
                break;
            case COMMENTS:
                Collections.sort(channels, SearchChannel.CommentComparator);
                break;
            default:
                System.out.println("Error !");
        }

                for(SearchChannel a : channels) {
                    System.out.println("Название: " + a.items[0].snippet.title);
                    System.out.println("Дата создания: " + a.items[0].snippet.publishedAt);
                    System.out.println("Количество подписчиков: " + a.items[0].statistics.subscriberCount);
                    System.out.println("Количество видео: " + a.items[0].statistics.videoCount);
                    System.out.println("Количество просмотров: " + a.items[0].statistics.viewCount);
                    System.out.println("Количество комментариев: " + a.commentCount);
                    System.out.println("------------------------------------");
        }
    }
}
