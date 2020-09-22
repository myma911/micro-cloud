package cn.aaron911.micro.im.webserver.util;

import cn.hutool.core.io.IoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

public class FilesInfo {

	public static int saveUserPicture(MultipartFile file, Long uid, String path) {
		int result = 0;
		if(StringUtils.isEmpty(file.getOriginalFilename())){
    	   return  result;
    	} 
    	String[]  name=	FileUtil.splitFileName(file.getOriginalFilename());
    	boolean b =  FileUtil.validateImgType(name[1]);
    	//文件类型验证通过 
    	if(b){
    	   try{
    		   File tempFile = new File(path, "B"+ uid+".jpg");
			   IoUtil.copy(file.getInputStream(), new FileOutputStream(tempFile));
	     	   result =1;
	     	   //大图裁剪
		     	 
	     	}catch(Exception e){
	     		e.printStackTrace();
	     	} 
    	} 
		return result;
	}
	
	public static String savePicture(MultipartFile file, String newname, String path) {
		String fileUrl = "";
		if(StringUtils.isEmpty(file.getOriginalFilename())){
    	   return  fileUrl;
    	} 
    	String[]  name=	FileUtil.splitFileName(file.getOriginalFilename());
    	boolean b =  FileUtil.validateImgType(name[1]);
    	//文件类型验证通过 
    	if(b){
    	   try{
    		   File tempFile = new File(path,  newname+"."+name[1]);
			   IoUtil.copy(file.getInputStream(), new FileOutputStream(tempFile));
	           fileUrl = newname+"."+name[1];
	     	   //大图裁剪
		     	 
	     	}catch(Exception e){
	     		e.printStackTrace();
	     	} 
    	} 
		return fileUrl;
	}
	
	public static String saveFiles(MultipartFile file,String newname, String path) {
		String fileUrl = "";
		if(StringUtils.isEmpty(file.getOriginalFilename())){
    	   return  fileUrl;
    	} 
    	String[]  name=	FileUtil.splitFileName(file.getOriginalFilename());
    	boolean b =  FileUtil.validateFileType(name[1]);
    	//文件类型验证通过 
    	if(b){
    	   try{
    		   fileUrl = newname+"."+name[1];
    		   File tempFile = new File(path,  newname+"."+name[1]);
			   IoUtil.copy(file.getInputStream(), new FileOutputStream(tempFile));

	     	}catch(Exception e){
	     		e.printStackTrace();
	     	} 
    	} 
		return fileUrl;
	} 

}
