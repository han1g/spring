package org.zerock.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10);//기본 규칙
	}
	
	public Criteria(int pageNum) {
		this(pageNum,10);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.keyword ="";
				
	}
	
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}

	public String getListLink() {
		Class<? extends Criteria> thisClass = this.getClass();
		Field[] fields  = thisClass.getDeclaredFields();
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		for(Field field : fields) {
			try {
				builder.queryParam(field.getName(), field.get(this));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//모든 필드의 값을 받아서 uriString으로 만듬
		return builder.toUriString();
	}
	
}
