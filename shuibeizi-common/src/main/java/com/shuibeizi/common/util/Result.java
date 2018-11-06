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
public class Result {

	private int code;
	private String msg;
	private Object data;

	public Result(){
		this.code = ResultCode.SUCCESS.getCode();
		this.msg = ResultCode.SUCCESS.getDesc();
		this.data = null;
	}

	public Result(ResultCode resultCode){
		this.code = resultCode.getCode();
		this.msg = resultCode.getDesc();
		this.data = null;
	}

	public Result(int code, String msg, Object data){
		this.code = code;
		this.msg = msg;
		this.data = data;

	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setPageData(Integer total, Object page, Object rows, Object data){
		PageResult pageResult = PageResult.getPageResult(total, page, rows, data);
		this.setData(pageResult);
	}

	public int getCode() {
		return code;
	}

	public Result setCode(int code) {
		this.code = code;
		return this;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", this.code);
		jsonObject.put("msg", this.msg);
		jsonObject.put("data", this.data != null ? this.data : "");
		return jsonObject.toString();
	}

	public static Result getSystemErrorMsg(){
		Result result = new Result(ResultCode.SYS_ERROR.getCode(),ResultCode.SYS_ERROR.getDesc(),null);
		return result;
	}

	public static Result getSystemErrorMsg(String errorMsg){
		if(StringUtils.isEmpty(errorMsg)) errorMsg = ResultCode.FAIL.getDesc();
		Result result = new Result(ResultCode.FAIL.getCode(),errorMsg,null);
		return result;
	}

	public static Result getSystemErrorMsg(Exception e){
		if(e instanceof CustomException){
			return Result.getSystemErrorMsg(e.getMessage());
		}else {
			return Result.getSystemErrorMsg("");
		}
	}

	public static Result getSuccessResult(){
		return new Result();
	}

	public static Result getFailedResult(String msg){
		return new Result(ResultCode.SYS_ERROR.getCode(), msg,null);
	}

	public static Result getForApiResult(ApiResult apiResult){
		Result result = new Result(apiResult.getCode(),apiResult.getMsg(),apiResult.getData());
		return result;
	}
}
