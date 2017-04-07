package com.zenkun.wunder.net;

import retrofit2.http.GET;
import rx.Observable;

import com.zenkun.wunder.model.ModelPlaceMarks;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public interface WunderApi {


@GET("wunderbucket/locations.json")
Observable<ModelPlaceMarks> getLocations();

}
