package com.yy.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yy.service.FileService;
import com.yy.vo.ImageVO;

@Controller
public class FileController {
	/**
	 * 文件上传时,参数名称必须一致.
	 * MultipartFile:SpringMVC 专门处理文件上传的
	 * 工具API
	 * 文件上传语法:   a.jpg
	 * 指定路径: D:\images\a.jpg
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	
	
	@RequestMapping("/fileTest")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		//1.准备文件目录
		String localFilePathString ="E:/99/file/test/";
		File fileDir = new File(localFilePathString);
		
		//如果文件目录不存在则新建目录
		if(!fileDir.exists()) {
			fileDir.mkdirs(); //多个层次,所以使用 mkdirs
		}
		
		//2.获取文件名称    利用 MultipartFile类 对象 获取 originalFilename -->翻译:原始文件名
		String originalFilename = fileImage.getOriginalFilename();
		
		//3.准备文件完整路径   目录 + 文件名字    
		String FilecompletePathString = localFilePathString+originalFilename;
		
		//4.文件传输   利用 MultipartFile类 对象 在新建文件/目录(内容格式不一样)  瞬间完成文件传输
		fileImage.transferTo(new File(FilecompletePathString));
		System.out.println("程序执行成功!!!");
		
		return "文件上传 ok!!!";
		
	}
	
	
	@Autowired
	private FileService fileService;
	
	/**
 http://localhost:9091/picture/upload?dir=image
Request Method: POST
Request Method: POST
Status Code: 404 

X-Requested-With: ShockwaveFlash/33.0.0.417
dir: image
Filename: 7.1.jpg
uploadFile: (binary)
Upload: Submit Query

	 * @param uploadFile
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/picture/upload")
	@ResponseBody
	public ImageVO pictureUpload(MultipartFile uploadFile) throws IllegalStateException, IOException {
		
		
		return fileService.upload(uploadFile);

	}
	

}
