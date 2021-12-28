package org.zerock.task;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.BoardAttachVO;
import org.zerock.mapper.BoardAttachMapper;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component//spring bean으로 등록시켜줌
public class FileCheckTask {

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	private String getFolderDateBefore(int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, - day);
		String str = sdf.format(cal.getTime());
		return str.replace("-", File.separator);
	}
	
	private List<File> getRemoveFiles(File[] targetDirFiles, List<Path> dbFileList) {
		ArrayList<File> ret = new ArrayList<File>();
		if(targetDirFiles== null)
			return ret;
		
		Arrays.parallelSort(targetDirFiles);// nlogn
		for(File file : targetDirFiles)
			log.info(file.getName());
		
		boolean[] arr = new boolean[targetDirFiles.length];
		dbFileList.forEach(path -> {
			File file = path.toFile();
			int x = Arrays.binarySearch(targetDirFiles, file);
			if(x>=0) {//db에 있는 파일
				arr[x] = true;
			}
		});
		for(int i = 0;i< arr.length;i++) {
			if(!arr[i])
				ret.add(targetDirFiles[i]);
		}
		ret.forEach(log::info);
		return ret;
	}
	
	public void checkFiles(int day) { // for test or manual execute
		List<BoardAttachVO> fileList = attachMapper.getOldFiles(day);
		
		List<Path> fileListPath = fileList.stream()
				.map(vo -> Paths.get("C:\\upload\\temp",vo.getUploadPath(),vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());//VO들을 Path로 매핑후 리스트로
		
		fileList.stream().filter(vo -> vo.isFileType())
		.map(vo -> Paths.get("C:\\upload\\temp",vo.getUploadPath(),"s_" + vo.getUuid() + "_" + vo.getFileName()))
		.forEach(p-> fileListPath.add(p));
		//썸네일파일도 리스트에 추가
		
		File targetDir = Paths.get("C:\\upload\\temp",getFolderDateBefore(day)).toFile();
		log.info(getFolderDateBefore(day));
		File[] targetDirFileList = targetDir.listFiles();
		getRemoveFiles(targetDirFileList, fileListPath).forEach(File::delete);
	}
	
	@Scheduled(cron="0 0 2 * * *")
	public void checkFiles() throws Exception {
		log.warn("오전 2시마다 실행---------------------------------");
		checkFiles(1);
		
	}
	
	
}
