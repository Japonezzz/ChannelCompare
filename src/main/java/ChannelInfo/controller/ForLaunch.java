package ChannelInfo.controller;

import ChannelInfo.enums.SortKey;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ForLaunch {

    public static void main(String[] args) throws UnirestException {
        //ForMapper.searchChannel("UCe4qOizEHWkk2N9m3CyfI4Q");
        //ForMapper.compareChannel("UC2-3h_JwOe0TUNc7fYqRBog", "UCe4qOizEHWkk2N9m3CyfI4Q");
        String[] id = {"UC2-3h_JwOe0TUNc7fYqRBog", "UCe4qOizEHWkk2N9m3CyfI4Q"};
        //ForMapper.Sorting(id, SortKey.SUBSCRIBERS);

        //ForMapper.GetCommentCount("UC2-3h_JwOe0TUNc7fYqRBog");
        //ForMapper.compareChannel("UC2-3h_JwOe0TUNc7fYqRBog", "UCe4qOizEHWkk2N9m3CyfI4Q");
        // String[] id = {"UC2-3h_JwOe0TUNc7fYqRBog", "UCe4qOizEHWkk2N9m3CyfI4Q"};
//        ForMapper.Sorting(id, SortKey.SUBSCRIBERS);
        //ForMapper.SearchChannelComment("UC2-3h_JwOe0TUNc7fYqRBog");
        //ForMapper.compareChannelComment("UC2-3h_JwOe0TUNc7fYqRBog", "UCe4qOizEHWkk2N9m3CyfI4Q");

        ForMapper.SortingWithComment(id, SortKey.COMMENTS);
    }
}