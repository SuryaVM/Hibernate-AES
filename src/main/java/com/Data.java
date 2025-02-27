package com;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Data {
	@Id
	private String plain_txt;
	private String cipher_txt;
	private String secret_key;
	private String initial_vector;

	public String getPlain_txt() {
		return plain_txt;
	}

	public void setPlain_txt(String plain_txt) {
		this.plain_txt = plain_txt;
	}

	public String getCipher_txt() {
		return cipher_txt;
	}

	public void setCipher_txt(String cipher_txt) {
		this.cipher_txt = cipher_txt;
	}

	public String getKey() {
		return secret_key;
	}

	public void setKey(String key) {
		this.secret_key = key;
	}

	public String getInitial_vector() {
		return initial_vector;
	}

	public void setInitial_vector(String initial_vector) {
		this.initial_vector = initial_vector;
	}

	@Override
	public String toString() {
		return "Data [plain_txt=" + plain_txt + ", cipher_txt=" + cipher_txt + ", secret_key=" + secret_key
				+ ", initial_vector=" + initial_vector + "]";
	}
	
}
