package CommentInfo.controller;

import CommentInfo.model.CommentModel.SearchCountComment;
import CommentInfo.model.SearchComment;
import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.Collections;

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

  public static void GetCommentCount (String ChannelID) throws UnirestException {
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
      System.out.println("total comment = "+totalCommentCount);
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

//    public static void compareChannel (String ChannelID, String ChannelID1) throws UnirestException {
//        HttpResponse<SearchCountComment> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
//                .queryString("key", APIkey)
//                .queryString("part", "snippet,statistics")
//                .queryString("id", ChannelID)
//                .asObject(SearchCountComment.class);
//
//        HttpResponse<SearchCountComment> response1 = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
//                .queryString("key", APIkey)
//                .queryString("part", "snippet,statistics")
//                .queryString("id", ChannelID1)
//                .asObject(SearchCountComment.class);
//
//        for (int i = 0; i < response.getBody().items.length; i++) {
//            System.out.println("title: " + response.getBody().items[i].snippet.title);
//            System.out.println("data sozdanija: " + response.getBody().items[i].snippet.publishedAt);
//            System.out.println("sub count: " + response.getBody().items[i].statistics.subscriberCount);
//            System.out.println("video count: " + response.getBody().items[i].statistics.videoCount);
//            System.out.println("view count: " + response.getBody().items[i].statistics.viewCount);
//            System.out.println("------------------------------------");
//            System.out.println("title: " + response1.getBody().items[i].snippet.title);
//            System.out.println("data sozdanija: " + response1.getBody().items[i].snippet.publishedAt);
//            System.out.println("sub count: " + response1.getBody().items[i].statistics.subscriberCount);
//            System.out.println("video count: " + response1.getBody().items[i].statistics.videoCount);
//            System.out.println("view count: " + response1.getBody().items[i].statistics.viewCount);
//        }
//    }
//
//    public static void Sorting (String [] ChannelID, SortKey key) throws UnirestException {
//        ArrayList<SearchCountComment> channels = new ArrayList<SearchCountComment>();
//        for(int j=0; j<ChannelID.length;j++) {
//          HttpResponse<SearchCountComment> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
//                  .queryString("key", APIkey)
//                  .queryString("part", "snippet,statistics")
//                  .queryString("id", ChannelID[j])
//                  .asObject(SearchCountComment.class);
//          channels.add(response.getBody());
//      }
//
//      switch (key) {
//          case NAME:
//              Collections.sort(channels, SearchCountComment.NameComparator);
//              break;
//          case DATE:
//              Collections.sort(channels, SearchCountComment.DateComparator);
//              break;
//          case SUBSCRIBERS:
//              Collections.sort(channels, SearchCountComment.SubsComparator);
//              break;
//          case VIDEOS:
//              Collections.sort(channels, SearchCountComment.VideosComparator);
//              break;
//          case VIEWS:
//              Collections.sort(channels, SearchCountComment.ViewsComparator);
//              break;
//              default:
//                  System.out.println("Error !");
//                  return;
//      }
//
//        for(SearchCountComment a : channels) {
//            System.out.println("title: " + a.items[0].snippet.title);
//            System.out.println("data sozdanija: " + a.items[0].snippet.publishedAt);
//            System.out.println("sub count: " + a.items[0].statistics.subscriberCount);
//            System.out.println("video count: " + a.items[0].statistics.videoCount);
//            System.out.println("view count: " + a.items[0].statistics.viewCount);
//            System.out.println("------------------------------------");
//        }
//    }
}
