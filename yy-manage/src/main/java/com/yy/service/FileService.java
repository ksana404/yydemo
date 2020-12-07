package com.yy.service;

import org.springframework.web.multipart.MultipartFile;

import com.yy.vo.ImageVO;

public interface FileService {

	/**文件上传的 接口 
	 * @param uploadFile
	 * @return
	 */
	ImageVO upload(MultipartFile uploadFile);

}
