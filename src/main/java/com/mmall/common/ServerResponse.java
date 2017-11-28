package com.mmall.common;
import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * �÷��͹���ķ������Ӧ����
 * 
 * @author ������
 *
 * @param <T>
 */
//ע�����˼�� ������л���������û��msg����dataʱ����ʲô����������ʾnull
//��֤���л�json��ʱ�������null�Ķ���keyҲ����ʧ
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
	private int status;
	private String msg;
	private T data;
	
	//�ĸ����캯��
	private ServerResponse(int status) {
		this.status = status;
	}
	
	private ServerResponse(int status,String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	private ServerResponse(int status,String msg,T data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	private ServerResponse(int status,T data) {
		this.status = status;
		this.data = data;
	}
	
	//ͨ��ö���� ����status��ֵ �жϵ�¼״̬
	// public �������л���json  ע�����
	//ʹ֮�������л�json�Ľ������
	@JsonIgnore
	public boolean isSuccess() {
		return this.status == ResponseCode.SUCCESS.getCode();
	}
	
	//���⿪��
	public int getStatus() {
		return status;
	}
	
	public T getData() {
		return data;
	}
	
	public String getMsg() {
		return msg;
	}
	
	//��̬�������� �����ɷ���
	
	//�����ɹ���Status
	public static <T> ServerResponse<T> createBySuccess(){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
	}
	
	public static <T> ServerResponse<T> createBySuccessMessage(String msg){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
		
	}
	
	public static <T> ServerResponse<T> createBySuccess(T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
	}
	
	public static <T> ServerResponse<T> createBySuccess(String msg,T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
	}
	
	//ʧ�ܵ�
	
	public static <T> ServerResponse<T> createByError(){
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
	}
	
	public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
	}
	
	public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
		return new ServerResponse<T>(errorCode,errorMessage);
	}
}


















