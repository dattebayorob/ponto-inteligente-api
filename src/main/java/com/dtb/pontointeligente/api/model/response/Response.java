package com.dtb.pontointeligente.api.model.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
	
	/**
	 * Encapsular retorno da api
	 * 
	 * */
	
	private T data;
	private List<String> erros;
	public Response() {
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public List<String> getErros() {
		if (this.erros == null) {
			this.erros = new ArrayList<String>();
		}
		return this.erros;
	}
	public void setErros(List<String> erros) {
		this.erros = erros;
	}
	
}
