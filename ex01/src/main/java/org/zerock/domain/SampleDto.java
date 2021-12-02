package org.zerock.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SampleDto {
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	//url파라미터로 주고받을 때 다음 포맷에 맞는 스트링으로 통신가능
	private Date dueDate;

	private String dueDate2;
	
}
