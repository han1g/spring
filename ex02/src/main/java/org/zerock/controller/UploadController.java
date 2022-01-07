package org.zerock.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.BoardAttachVO;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

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
	private boolean checkImageType(File file) {
		String contentType;
		try {
			contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	@PostMapping(value = {"/uploadFormAction","/relative/uploadFormAction"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<List<BoardAttachVO>> uploadFormPost(MultipartFile[] uploadFile) throws IOException {
		String datePath = getFolder();
		File uploadPath = new File("C:\\upload\\temp",datePath);
		if(!uploadPath.exists()) {//����� root�� C
			log.info(uploadPath.mkdirs());//recursive
		}
		
		
		ArrayList<BoardAttachVO> retList = new ArrayList<>(); 
		for(MultipartFile multipartFile : uploadFile) {
			String fileName = multipartFile.getOriginalFilename();
			log.info(multipartFile.getName());//<�Ķ���͸�>
			log.info(fileName);//�ø��� ���� �̸�
			log.info(multipartFile.isEmpty());//������ ����
			log.info(multipartFile.getSize());//����ũ��
			log.info(multipartFile.getBytes().length);//����Ʈ �迭�� ��ȯ
			log.info(multipartFile.getInputStream());//���ϵ����͸� �޴� inputstream
			
			
			
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			
			UUID uuid = UUID.randomUUID();
			fileName = uuid.toString() + "_" + fileName;
			File savefile = new File(uploadPath,fileName);
			log.info(savefile.getName());
			boolean isImage = false;
			try {
				multipartFile.transferTo(savefile);
				if(isImage = checkImageType(savefile)) {
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath,"s_"+ fileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				
				
				
			}catch (Exception e) {
				log.error(e.getMessage());
			}
			BoardAttachVO ret = new BoardAttachVO();
			ret.setFileName(multipartFile.getOriginalFilename().substring(fileName.lastIndexOf("\\") + 1));
			ret.setFileType(isImage);
			ret.setUploadPath(datePath);
			ret.setUuid(uuid.toString());
			retList.add(ret);
			
		}
		
		return new ResponseEntity<>(retList,HttpStatus.OK);
	}
	@GetMapping("/thumbnail") 
	public @ResponseBody ResponseEntity<byte[]> getThumb(String fileName) {
		File file = new File("C:\\upload\\temp\\" + fileName);
		
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders header = new HttpHeaders();
			log.info(Files.probeContentType(file.toPath()));
			header.add("Content-Type",Files.probeContentType(file.toPath()));//�̹����� MIME
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();// TODO: handle exception
		}
	
		return result;
		
	}
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody ResponseEntity<Resource> downloadFile(String pathAndUuid,String fileName) throws IOException {
		log.info(pathAndUuid);
		log.info(fileName);
		Resource resource = new FileSystemResource("C:\\upload\\temp\\" + pathAndUuid + "_" + fileName);
		log.info(resource.contentLength());
		
		String resourceName = URLEncoder.encode(fileName,"UTF-8").replace("+", "%20");//urlEncoder 띄어쓰기가 +로 됨
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename*= UTF-8''" + resourceName);//다운로드 파일 인코딩
		log.info(resourceName);
		
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	}
	
	@PostMapping("deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName,boolean type) {
		
		File file;
		log.info(fileName);
		try {
			file = new File("C:\\upload\\temp\\" + fileName);
			
			file.delete();
			if(type) {
				String thumbnailPath = file.getAbsolutePath();
				StringBuffer thumbnailPathBuffer = new StringBuffer(thumbnailPath);
				
				thumbnailPathBuffer.insert(thumbnailPath.lastIndexOf("\\") + 1,"s_");
				thumbnailPath = thumbnailPathBuffer.toString();
				log.info(thumbnailPath);
				File thumbnailFile = new File(thumbnailPath);
				thumbnailFile.delete();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ResponseEntity<String>("success",HttpStatus.OK);
		
	}
	
}
