package com.fzubb.client.response;

import lombok.Data;

import java.text.MessageFormat;

@Data
public class BaseResponse<T> {
       int code;
       String message;
       T data;
      /**业务情况类型*/
      public enum  BussinessEnum{
               success(0,"成功"),
          param_error(-1,"{0}参数错误"),
          unbind_student(-2,"未绑定教务处账号"),
          operate_frequent(-3,"{0}操作频繁，稍后再试");

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
    public  static  <T> BaseResponse<T> operate_freequent(String param){
        BaseResponse<T> baseResponse=new BaseResponse<T>();
        baseResponse.setCode(BussinessEnum.operate_frequent.getCode());
        baseResponse.setMessage(MessageFormat.format(BussinessEnum.operate_frequent.getMessage(),param));
        return  baseResponse;
    }

}
