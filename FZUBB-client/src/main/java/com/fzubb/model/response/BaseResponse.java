package com.fzubb.model.response;

import lombok.Data;

import java.text.MessageFormat;

@Data
public class BaseResponse<T> {
       int code;
       String message;
       T data;

      public enum  BussinessEnum{
               success(0,"成功"),param_error(-1,"{0}参数错误"),unbind_student(-2,"未绑定教务处账号");
               int code;
               String message;
                BussinessEnum(int code,String message){
                      this.code=code;
                      this.message=message;
               }

             public int getCode() {
                    return code;
             }

             public void setCode(int code) {
                    this.code = code;
             }

             public String getMessage() {
                    return message;
             }

             public void setMessage(String message) {
                    this.message = message;
             }
      }

      public  static  <T> BaseResponse<T> success(T data){
              BaseResponse<T> baseResponse=new BaseResponse<T>();
              baseResponse.setData(data);
              baseResponse.setCode(BussinessEnum.success.getCode());
              baseResponse.setMessage(BussinessEnum.success.getMessage());
              return  baseResponse;
      }
      public  static  <T> BaseResponse<T> param_error(String param){
              BaseResponse<T> baseResponse=new BaseResponse<T>();
              baseResponse.setCode(BussinessEnum.param_error.getCode());
              baseResponse.setMessage(MessageFormat.format(BussinessEnum.param_error.getMessage(),param));
              return  baseResponse;
       }
    public  static  <T> BaseResponse<T> unbind(){
        BaseResponse<T> baseResponse=new BaseResponse<T>();
        baseResponse.setCode(BussinessEnum.unbind_student.getCode());
        baseResponse.setMessage(MessageFormat.format(BussinessEnum.unbind_student.getMessage(),null));
        return  baseResponse;
    }

}
