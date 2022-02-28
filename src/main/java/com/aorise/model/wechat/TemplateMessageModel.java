package com.aorise.model.wechat;

import lombok.Data;

import java.util.List;

/**
 * @author cat
 * @Description 模板消息model
 * @date   2018-04-12  10:00
 * @modified By:
 */
@Data
public class TemplateMessageModel {

	private String accessToken;
	private String openId;
	private String url;
	private String templateId;
	private String first;
	private List<String> keywords;
	private String remark;

}
