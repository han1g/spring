package org.zerock.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SampleDto {
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	//url�Ķ���ͷ� �ְ���� �� ���� ���˿� �´� ��Ʈ������ ��Ű���
	private Date dueDate;

	private String dueDate2;
	
}
