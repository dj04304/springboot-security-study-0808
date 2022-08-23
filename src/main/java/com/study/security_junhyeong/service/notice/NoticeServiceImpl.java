package com.study.security_junhyeong.service.notice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.security_junhyeong.domain.notice.Notice;
import com.study.security_junhyeong.domain.notice.NoticeFile;
import com.study.security_junhyeong.domain.notice.NoticeRepository;
import com.study.security_junhyeong.web.dto.notice.AddNoticeReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
	
	@Value("${file.path}")
	private String filePath;
	
	private final NoticeRepository noticeRepository;
	
	@Override
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception {
		Predicate<String> predicate = (filename) -> !filename.isBlank();
		
		Notice notice = null;
		
		
		notice = Notice.builder()
				.notice_title(addNoticeReqDto.getNoticeTitle())
				.user_code(addNoticeReqDto.getUserCode())
				.notice_content(addNoticeReqDto.getIr1())
				.build();
		
		noticeRepository.saveNotice(notice);
		
		//파일이 있는 경우에만 if문이 실행이 된다.
		if(predicate.test(addNoticeReqDto.getFile().get(0).getOriginalFilename())) {
			List<NoticeFile> noticeFiles =  new ArrayList<NoticeFile>();
			
			for(MultipartFile file : addNoticeReqDto.getFile()) {
				String originalFilename = file.getOriginalFilename();
				String tempFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFilename; //겹칠수 없는 랜덤의 문자열을 만들어준다.(범용 고유 식별자) 유저들이 올리는 파일명이 겹칠 수 있기 때문에 사용한다.
				log.info(tempFilename); //UUID + "_" + 파일명
				Path uploadPath = Paths.get(filePath, "notice/" + tempFilename); //파일의 전체 경로
				
				File f = new File(filePath + "notice");
				if(!f.exists()) { //파일 경로가 존재하지 않으면
					f.mkdirs(); //경로를 만들어라
					
				}
				
				try {
					Files.write(uploadPath, file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				noticeFiles.add(NoticeFile.builder().notice_code(notice.getNotice_code()).file_name(tempFilename).build());
			}
			
			noticeRepository.saveNoticeFiles(noticeFiles);
			
		}
		
		
		return notice.getNotice_code();
	}

}
