package com.toyproject.lineupproject.controller.api;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.*;

@Component
public class APIPlaceHandler {
    // 함수형 API 구현 하기위한 핸들러
    public ServerResponse getPlaces(ServerRequest request)  {
        return ok().body(List.of("place1","place2"));
    }

    public ServerResponse createPlace(ServerRequest request)  {
        // TODO: 구현할때 수정해줄것,
        return created(URI.create("/api/places/1")).body(true);
    }

    public ServerResponse getPlace(ServerRequest request)  {
        return ok().body("Place " + request.pathVariable("placeId"));
    }

    public ServerResponse modifyPlace(ServerRequest request)  {
        return ok().body(true);
    }

    public ServerResponse removePlace(ServerRequest request)  {
        return ok().body(true);
    }


}
