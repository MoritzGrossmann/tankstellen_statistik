package com.grossmann.gasstation.collector.core;

import javax.ws.rs.core.MediaType;
import java.util.Map;

public interface Request<T,A> {

    String getPath();

    String setPath(String path);

    void concatPath(String path);

    void addQueryParams(Map<String, String> queryParams);

    Map<String, String> getQueryParams();

    RequestMethod getRequestMethod();

    Class<T> getResponseClass();

    Class<A> getAlternativResponseClass();

    MediaType getRequestedMediaType();

    MediaType getPayloadMediaType();

    Object getPayload();
}
