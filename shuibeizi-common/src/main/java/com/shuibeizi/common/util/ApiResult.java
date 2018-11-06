package com.shuibeizi.common.util;

import com.shuibeizi.common.enumerate.ResultCode;
import com.shuibeizi.common.exception.CustomException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Description:
 * @author:wanqing
 * @date:2016-9-19
 */
public class ApiResult<T> {

	private int code;
	private String msg;
	private T data;

	public ApiResult(){
		this.code = ResultCode.SUCCESS.getCode();
		this.msg = ResultCode.SUCCESS.getDesc();
		this.data = null;
	}

	public ApiResult(ResultCode resultCode){
		this.code = resultCode.getCode();
		this.msg = resultCode.getDesc();
		this.data = null;
	}

	public ApiResult(int code, String msg, T t){
		this.code = code;
		this.msg = msg;
		this.data = t;

	}
	
	public String getMsg() {
		return msg;
	}
	public ApiResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public ApiResult setData(T data) {
		this.data = data;
		return this;
	}

	public int getCode() {
		return code;
	}

	public ApiResult setCode(int code) {
		this.code = code;
		return this;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", this.code);
		jsonObject.put("msg", this.msg);
		jsonObject.put("data", this.data);
		return jsonObject.toString();
	}

	public static ApiResult getSystemErrorMsg(){
		ApiResult result = new ApiResult(ResultCode.SYS_ERROR.getCode(),ResultCode.SYS_ERROR.getDesc(),null);
		return result;
	}

	public static ApiResult getSystemErrorMsg(String errorMsg){
		if(StringUtils.isEmpty(errorMsg)) errorMsg = ResultCode.FAIL.getDesc();
		ApiResult result = new ApiResult(ResultCode.FAIL.getCode(),errorMsg,null);
		return result;
	}

	public static ApiResult getSystemErrorMsg(Exception e){
		if(e instanceof CustomException){
			return ApiResult.getSystemErrorMsg(e.getMessage());
		}else {
			return ApiResult.getSystemErrorMsg("");
		}
	}

	public static ApiResult getSuccessResult(){
		return new ApiResult();
	}

	public static <T> ApiResult getSuccessResult(T t){
		ApiResult<T> apiResult = new ApiResult<T>();
		apiResult.setData(t);
		return apiResult;
	}

	public static ApiResult getFailedResult(String msg){
		return new ApiResult(ResultCode.SYS_ERROR.getCode(), msg,null);
	}

	public static boolean isSuccess(ApiResult apiResult){
		if(apiResult.getCode() == ResultCode.SUCCESS.getCode()){
			return true;
		}
		return false;
	}
}
