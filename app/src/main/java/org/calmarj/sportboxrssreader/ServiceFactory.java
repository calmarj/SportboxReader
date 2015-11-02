package org.calmarj.sportboxrssreader;

import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by calmarj on 02.11.15.
 */
public class ServiceFactory {


    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endPoint)
                .setConverter(new SimpleXMLConverter())
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}