package com.yy.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yy.service.FileService;
import com.yy.vo.ImageVO;

/**文件上传的业务实现
 * @author Administrator
 *
 */
@PropertySource("classpath:/properties/image.prpperties")
@Service
public class FileServiceImpl implements FileService{
	/**
	 * 
	 image.localDir=E:/99/file/yy/images
	 image.httpUrl=http://image.yy.com
	 * */
	
	@Value("${image.localDir}")
	private String localDir;
	@Value("${image.httpUrl}")
	private String httpUrl;

	@Override
	public ImageVO upload(MultipartFile uploadFile) {
		/**
		 * 真是图片信息: abc.jpg
		 * 实现文件上传思路
		 * 1.判断是否为图片类型  jpg|png|gif
		 * 2.判断是否为恶意程序  
		 * 3.图片必须分目录保存 类目/时间 yyyy/MM/dd
		 * 4.防止图片重名,自定义名称 UUID.类型
		 * 
		 * 正则案例:
		 * 	要求:名称是英文的   [a-z]+
		 *  要求:名称都是中文   [^a-z][^A-Z][^0-9]  匹配不在  [a-z],[A-Z],[0-9]这些区间的数据. 
		 */
		
		//0.参数校验 通过获取文件名后缀来校验文件     利用 MultipartFile类 对象 获取 originalFilename -->翻译:原始文件名
		
		//0.获取文件 原始名字
		String originalFilename = uploadFile.getOriginalFilename();
		//1.文件名有效性校验
		 originalFilename = originalFilename.toLowerCase();
		if(!originalFilename.matches("^.+\\.(jpg|png|gif)$")) {
			return ImageVO.fail();
		}
		
		
		//2.是否为恶意程序 主要判断图片属性width和height
				try {
					BufferedImage bufferedImage = 
							ImageIO.read(uploadFile.getInputStream());
					int width = bufferedImage.getWidth();
					int height = bufferedImage.getHeight();
					
					if(width ==0 || height==0) {
						
						return ImageVO.fail();
					}
					

					
					
					//3.实现分目录储存 本地磁盘路径+日期目录
					String dataDir = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
					
					//全部 真实路径 就是 E:/99/file/yy/images /yyyy/MM/dd/ kdfasds - image.jpg
			String fileLocalDatePath =	localDir+dataDir;
					File localDatefile = new File(fileLocalDatePath); // E:/99/file/yy/images /yyyy/MM/dd/
			//如果路径不存在则新建
			if(!localDatefile.exists()) {
				localDatefile.mkdirs();
			}
			//为了防止重名 需要加上UUID作为 新增加的文件名字点缀
			String fileName = UUID.randomUUID().toString()+"-"+originalFilename; //kdfasds888 - image.jpg
			
			//拼接完整的 文件路径 目录+名字
			
			String completeFileName =fileLocalDatePath+fileName;
			//传输
			uploadFile.transferTo(new File(completeFileName));
			
			//拼接虚拟地址
			String url = httpUrl+dataDir+fileName;
			
			//返回文件对象 信息,以及传输提示		
			return new ImageVO(0, url, width, height);
		
		
					
					} catch (Exception e) {
					
						return ImageVO.fail();
					}
						
					
					
	}

}
