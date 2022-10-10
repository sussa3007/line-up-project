package com.toyproject.lineupproject.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class APIDateResponse extends com.toyproject.lineupproject.dto.APIErrorResponse{
    private final Object data;

    private APIDateResponse(Boolean success, Integer errorCode, String message, Object data){
        super(success,errorCode,message);
        this.data = data;
    }
}
