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

    public static Callable<SearchChannel> GetResponseChannel (String id)
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


  public static void searchChannel (String ChannelID) throws ExecutionException, InterruptedException {
      executorService = Executors.newFixedThreadPool(1);
      Future<SearchChannel> future = executorService.submit(GetResponseChannel(ChannelID));
      executorService.shutdown();

      for (int i = 0; i < future.get().items.length; i++) {
          System.out.println("Название: " + future.get().items[i].snippet.title);
          System.out.println("Дата создания: " + future.get().items[i].snippet.publishedAt);
          System.out.println("Количество подписчиков: " + future.get().items[i].statistics.subscriberCount);
          System.out.println("Количество видео: " + future.get().items[i].statistics.videoCount);
          System.out.println("Количество просмотров: " + future.get().items[i].statistics.viewCount);
          System.out.println("------------------------------------");
      }

  }




    public static void compareChannel (String ChannelID, String ChannelID1) throws ExecutionException, InterruptedException {

        executorService = Executors.newFixedThreadPool(2);
        Future<SearchChannel> future = executorService.submit(GetResponseChannel(ChannelID));
        Future<SearchChannel> future1 = executorService.submit(GetResponseChannel(ChannelID1));
        executorService.shutdown();

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

    }

    public static void Sorting (String [] ChannelID, SortKey key) throws ExecutionException, InterruptedException {

      ArrayList<SearchChannel> channels = new ArrayList<SearchChannel>();
        executorService = Executors.newFixedThreadPool(ChannelID.length);

        for(int j=0; j<ChannelID.length;j++) {
            Future<SearchChannel> future = executorService.submit(GetResponseChannel(ChannelID[j]));
          channels.add(future.get());
      }
      executorService.shutdown();

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

    public static Callable<SearchComment> GetResponseSearch (String ChannelID, String token)
    {
        Callable<SearchComment> response =() -> {
            HttpResponse<SearchComment> r = Unirest.get("https://www.googleapis.com/youtube/v3/search")
                    .queryString("key", APIkey)
                    .queryString("part", "snippet")
                    .queryString("maxResults", 50)
                    .queryString("channelId", ChannelID)
                    .queryString("pageToken", token)
                    .asObject(SearchComment.class);
            return r.getBody();
        };
        return response;
    }

    public static Callable<SearchComment> GetResponseSearchTest (String ChannelID, String token)
    {
        Callable<SearchComment> response =() -> {
            HttpResponse<SearchComment> r = Unirest.get("https://www.googleapis.com/youtube/v3/search")
                    .queryString("key", APIkey)
                    .queryString("part", "snippet")
                    .queryString("maxResults", 50)
                    .queryString("channelId", ChannelID)
                    .queryString("pageToken", token)
                    .asObject(SearchComment.class);
            return r.getBody();
        };
        return response;
    }


    public static int GetCommentCount (String ChannelID) throws UnirestException, ExecutionException, InterruptedException {

        long start = System.currentTimeMillis()/1000;
        HttpResponse<SearchComment> response = Unirest.get("https://www.googleapis.com/youtube/v3/search")
                .queryString("key", APIkey)
                .queryString("part", "snippet")
                .queryString("maxResults", 50)
                .queryString("channelId", ChannelID)
                .asObject(SearchComment.class);

        int totalCommentCount = GetCountComment(response.getBody());
        String nextToken = response.getBody().nextPageToken;

        while (nextToken != null) {
            final String x;
            x=nextToken;
            executorService = Executors.newFixedThreadPool(10);
            Future<SearchComment> future = executorService.submit(GetResponseSearch(ChannelID, x));
            executorService.shutdown();

            totalCommentCount += GetCountComment(future.get());
            nextToken = future.get().nextPageToken;
        }

        long finish = System.currentTimeMillis()/1000;
        System.out.println(finish-start + " seconds");
        return totalCommentCount;

    }

    public static int GetCountComment(SearchComment array) throws ExecutionException, InterruptedException {

        executorService = Executors.newFixedThreadPool(10);
        int totalCommentCount = 0;
        for (int i = 0; i < array.items.length; i++) {
            if(array.items[i].id.videoId == null)
                continue;
            Future<SearchCountComment> future = executorService.submit(GetVideoComment(array.items[i].id.videoId));
            totalCommentCount += Integer.valueOf(future.get().items[0].statistics.commentCount);
        }
        executorService.shutdown();
        return totalCommentCount;
    }


    public static Callable<SearchCountComment> GetVideoComment(String videoId) {

        Callable<SearchCountComment> response =() -> {
            HttpResponse<SearchCountComment> r = Unirest.get("https://www.googleapis.com/youtube/v3/videos")
                    .queryString("key", APIkey)
                    .queryString("part", "statistics")
                    .queryString("id", videoId)
                    .asObject(SearchCountComment.class);
            return r.getBody();
        };
        return response;
    }



    public static void SearchChannelComment (String ChannelID) throws UnirestException, ExecutionException, InterruptedException {
        executorService = Executors.newFixedThreadPool(1);
        Future<SearchChannel> future = executorService.submit(GetResponseChannel(ChannelID));
        executorService.shutdown();

        for (int i = 0; i < future.get().items.length; i++) {
            System.out.println("Название: " + future.get().items[i].snippet.title);
            System.out.println("Дата создания: " + future.get().items[i].snippet.publishedAt);
            System.out.println("Количество подписчиков: " + future.get().items[i].statistics.subscriberCount);
            System.out.println("Количество видео: " + future.get().items[i].statistics.videoCount);
            System.out.println("Количество просмотров: " + future.get().items[i].statistics.viewCount);
            System.out.println("Количество комментариев: " + GetCommentCount(ChannelID) );
            System.out.println("------------------------------------");
        }

    }

    public static void compareChannelComment (String ChannelID, String ChannelID1) throws UnirestException, ExecutionException, InterruptedException {

        executorService = Executors.newFixedThreadPool(2);
        Future<SearchChannel> future = executorService.submit(GetResponseChannel(ChannelID));
        Future<SearchChannel> future1 = executorService.submit(GetResponseChannel(ChannelID1));
        executorService.shutdown();

        for (int i = 0; i < future.get().items.length; i++) {
            System.out.println("Название: " + future.get().items[i].snippet.title);
            System.out.println("Дата создания: " + future.get().items[i].snippet.publishedAt);
            System.out.println("Количество подписчиков: " + future.get().items[i].statistics.subscriberCount);
            System.out.println("Количество видео: " + future.get().items[i].statistics.videoCount);
            System.out.println("Количество просмотров: " + future.get().items[i].statistics.viewCount);
            System.out.println("Количество комментариев: " + GetCommentCount(ChannelID) );
            System.out.println("------------------------------------");
        }

        for (int i = 0; i < future1.get().items.length; i++) {
            System.out.println("Название: " + future1.get().items[i].snippet.title);
            System.out.println("Дата создания: " + future1.get().items[i].snippet.publishedAt);
            System.out.println("Количество подписчиков: " + future1.get().items[i].statistics.subscriberCount);
            System.out.println("Количество видео: " + future1.get().items[i].statistics.videoCount);
            System.out.println("Количество просмотров: " + future1.get().items[i].statistics.viewCount);
            System.out.println("Количество комментариев: " + GetCommentCount(ChannelID1) );
            System.out.println("------------------------------------");
        }
    }


    public static void SortingWithComment (String [] ChannelID) throws UnirestException, ExecutionException, InterruptedException {

        ArrayList<SearchChannel> channels = new ArrayList<SearchChannel>();



        for (int j = 0; j < ChannelID.length; j++) {
            executorService = Executors.newFixedThreadPool(1);
            Future<SearchChannel> future = executorService.submit(GetResponseChannel(ChannelID[j]));
            executorService.shutdown();
            SearchChannel channel = future.get();
            channel.commentCount = GetCommentCount(ChannelID[j]);
            channels.add(channel);
        }

        Collections.sort(channels, SearchChannel.CommentComparator);

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
