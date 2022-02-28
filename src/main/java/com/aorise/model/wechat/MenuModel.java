package com.aorise.model.wechat;

import lombok.Data;

/**
 * @author cat
 * @Description 公众号菜单Model
 * @date   2018-04-12  10:00
 * @modified By:
 */
@Data
public class MenuModel {

	private String id;
	private String name;
	private String type;
	private String key;
	private String url;


}
