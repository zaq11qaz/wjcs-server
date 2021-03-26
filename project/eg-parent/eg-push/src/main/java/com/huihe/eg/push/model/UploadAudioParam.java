package com.huihe.eg.push.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * class descr:语音上传处理
 *
 * @author zhangZhimin
 * @dataTime 2017-3-22 下午6:21:20
 */
public class UploadAudioParam {
	private byte[] b;// 字节格式的语音
	private MultipartFile[] file;
	private List<String> base64; // base64语音的格式
	private String user_id; // 用户id
	private String file_type = "sound";// 文件类型 sound
	private String speech_type; 

	public MultipartFile[] getFile() {
		return file;
	}

	public void setFile(MultipartFile[] file) {
		this.file = file;
	}

	public byte[] getB() {
		return b;
	}

	public void setB(byte[] b) {
		this.b = b;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public List<String> getBase64() {
		return base64;
	}

	public void setBase64(List<String> base64) {
		this.base64 = base64;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getSpeech_type() {
		return speech_type;
	}

	public void setSpeech_type(String speech_type) {
		this.speech_type = speech_type;
	}



}
