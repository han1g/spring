package org.zerock.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {
	
	@GetMapping({"/uploadForm","/relative/uploadForm","/uploadAjax"})
	public void uploadForm() {
		
		log.info("upload form");
	}
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);//CrossPlatform
		
	}
	@PostMapping({"/uploadFormAction","/relative/uploadFormAction"})
	public @ResponseBody String uploadFormPost(MultipartFile[] uploadFile, Model model) throws IOException {
		File uploadPath = new File("C:\\upload\\temp",getFolder());
		if(!uploadPath.exists()) {//����� root�� C
			log.info(uploadPath.mkdirs());//recursive
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			String fileName = multipartFile.getOriginalFilename();
			log.info(multipartFile.getName());//<�Ķ���͸�>
			log.info(fileName);//�ø��� ���� �̸�
			log.info(multipartFile.isEmpty());//������ ����
			log.info(multipartFile.getSize());//����ũ��
			log.info(multipartFile.getBytes().length);//����Ʈ �迭�� ��ȯ
			log.info(multipartFile.getInputStream());//���ϵ����͸� �޴� inputstream
			
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			
			File savefile = new File(uploadPath,fileName);
			File pathTest = new File("/",fileName);
			log.info(savefile.getName());
			try {
				multipartFile.transferTo(savefile);
				multipartFile.transferTo(pathTest);//���⼱ root�� web.xml���Ͼ��ε� ���
				
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return "success";
	}
}
