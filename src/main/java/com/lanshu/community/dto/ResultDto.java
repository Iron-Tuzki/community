package com.lanshu.community.dto;

import com.lanshu.community.exception.CustomerEecption;
import com.lanshu.community.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {

    private Integer code;
    private String message;

    public static ResultDto errorOf(Integer code, String message){
        ResultDto resultDto = new ResultDto();
        resultDto.code = code;
        resultDto.message = message;
        return resultDto;
    }

    public static ResultDto errorOf(ErrorCode notLogin) {
        return errorOf(notLogin.getCode(), notLogin.getMessage());
    }

    public static ResultDto errorOf(CustomerEecption e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDto okOf() {
        return new ResultDto(200,"请求成功");
    }
    //public static ResultDto errorOf()

}
