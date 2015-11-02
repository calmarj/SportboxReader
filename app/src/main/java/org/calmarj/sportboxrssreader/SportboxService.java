package org.calmarj.sportboxrssreader;

import org.calmarj.sportboxrssreader.retrofit.RSS;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by calmarj on 02.11.15.
 */
public interface SportboxService {
    String SERVICE_ENDPOINT = "http://news.sportbox.ru/taxonomy/term/";

    @GET("/118/0/feed")
    Observable<RSS> getFootballChannel();

    @GET("/152/0/feed")
    Observable<RSS> getHockeyChannel();

}
