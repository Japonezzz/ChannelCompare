package ChannelInfo.model;

import java.util.Comparator;

public class SearchChannel {
    public Item <Snippet, Statistics> [] items;
    public int commentCount;

    public static Comparator<SearchChannel> NameComparator = new Comparator<SearchChannel>() {

        public int compare(SearchChannel s1, SearchChannel s2) {
            String channeltitle1 = s1.items[0].snippet.title;
            String channeltitle2 = s2.items[0].snippet.title;

            //ascending order
            return channeltitle1.compareTo(channeltitle2);

            //descending order
            //return channeltitle2.compareTo(channeltitle1);
        }};

    public static Comparator<SearchChannel> DateComparator = new Comparator<SearchChannel>() {

        public int compare(SearchChannel s1, SearchChannel s2) {
            String channeltitle1 = s1.items[0].snippet.publishedAt;
            String channeltitle2 = s2.items[0].snippet.publishedAt;

            //ascending order
            return channeltitle1.compareTo(channeltitle2);

            //descending order
            //return channeltitle2.compareTo(channeltitle1);
        }};

    public static Comparator<SearchChannel> SubsComparator = new Comparator<SearchChannel>() {

        public int compare(SearchChannel s1, SearchChannel s2) {
            Integer channeltitle1 = Integer.valueOf(s1.items[0].statistics.subscriberCount);
            Integer channeltitle2 = Integer.valueOf(s2.items[0].statistics.subscriberCount);

            //ascending order
            return channeltitle1.compareTo(channeltitle2);

            //descending order
            //return channeltitle2.compareTo(channeltitle1);
        }};

    public static Comparator<SearchChannel> VideosComparator = new Comparator<SearchChannel>() {

        public int compare(SearchChannel s1, SearchChannel s2) {
            Integer channeltitle1 = Integer.valueOf(s1.items[0].statistics.videoCount);
            Integer channeltitle2 = Integer.valueOf(s2.items[0].statistics.videoCount);

            //ascending order
            return channeltitle1.compareTo(channeltitle2);

            //descending order
            //return channeltitle2.compareTo(channeltitle1);
        }};

    public static Comparator<SearchChannel> ViewsComparator = new Comparator<SearchChannel>() {

        public int compare(SearchChannel s1, SearchChannel s2) {
            Integer channeltitle1 = Integer.valueOf(s1.items[0].statistics.viewCount);
            Integer channeltitle2 = Integer.valueOf(s2.items[0].statistics.viewCount);

            //ascending order
            return channeltitle1.compareTo(channeltitle2);

            //descending order
            //return channeltitle2.compareTo(channeltitle1);
        }};

    public static Comparator<SearchChannel> CommentComparator = new Comparator<SearchChannel>() {

        public int compare(SearchChannel s1, SearchChannel s2) {
            Integer comments1 = s1.commentCount;
            Integer comments2 = s2.commentCount;
            //ascending order
            //return comments1.compareTo(comments2);

            //descending order
            return comments2.compareTo(comments1);
        }};
}
