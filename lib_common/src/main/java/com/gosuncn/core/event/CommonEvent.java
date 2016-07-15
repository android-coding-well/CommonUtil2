package com.gosuncn.core.event;

public class CommonEvent<T> implements IEvent {

	/**
	 * 事件代码
	 */
	public int code;
	/**
	 * 内容
	 */
	public String text=null;

	/**
	 * 对象
	 */
	public T t=null;
	
	public CommonEvent(int code){
		this.code =code;
	}
	public CommonEvent(int code,String text){
		this(code);
		this.text=text;
	}
	public CommonEvent(int code,T t){
		this(code);
		this.t=t;
	}
	public CommonEvent(int code,String text,T t){
		this(code,text);
		this.t=t;
	}
}
