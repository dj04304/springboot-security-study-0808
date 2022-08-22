package com.study.security_junhyeong.service.notice;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.study.security_junhyeong.web.dto.notice.AddNoticeReqDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Value("${file.path}")
	private String filePath;
	
	@Override
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception {
		String originalFilename = addNoticeReqDto.getFile().get(0).getOriginalFilename();
		
		
		//파일이 없는 경우 (파일업로드를 하면 안됨), else일 때 파일 업로드 하게끔 함
		if(originalFilename.isBlank()) {
			
		}else {
			String tempFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFilename; //겹칠수 없는 랜덤의 문자열을 만들어준다.(범용 고유 식별자) 유저들이 올리는 파일명이 겹칠 수 있기 때문에 사용한다.
			log.info(tempFilename); //UUID + "_" + 파일명
		}
		return 0;
	}

}
