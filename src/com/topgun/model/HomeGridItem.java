package com.topgun.model;

/**
 * 首页区域子模块类
 * 
 * @author liusx
 *
 */
public class HomeGridItem {

	String text;
	int imageResouce;
	
	public HomeGridItem(String text, int image_resouce) {
		super();
		this.text = text;
		this.imageResouce = image_resouce;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getImage_resouce() {
		return imageResouce;
	}

	public void setImage_resouce(int image_resouce) {
		this.imageResouce = image_resouce;
	}

}
