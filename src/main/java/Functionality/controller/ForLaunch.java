package Functionality.controller;

import Functionality.enums.SortKey;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ForLaunch {

    public static void main(String[] args) throws UnirestException {
        //ForMapper.searchChannel("UCe4qOizEHWkk2N9m3CyfI4Q");
        //ForMapper.compareChannel("UC2-3h_JwOe0TUNc7fYqRBog", "UCe4qOizEHWkk2N9m3CyfI4Q");
        //String[] id = {"UC2-3h_JwOe0TUNc7fYqRBog", "UCe4qOizEHWkk2N9m3CyfI4Q"};
        //ForMapper.Sorting(id, SortKey.SUBSCRIBERS);

        ForMapper.mediaRezonans("UC2-3h_JwOe0TUNc7fYqRBog");
        //ForMapper.compareMediarezonans("UC2-3h_JwOe0TUNc7fYqRBog", "UCe4qOizEHWkk2N9m3CyfI4Q");
    }
}