package model;

import beans.entry.HeaderImage;

public class Resource {

	private String title;
	private String content;
	private String link;
	private HeaderImage mainImage;
	private String pageName;
	private String pageContent;
	private String slug;
	private String linkLabel;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public HeaderImage getMainImage() {
		return mainImage;
	}
	public void setMainImage(HeaderImage mainImage) {
		this.mainImage = mainImage;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getLinkLabel() {
		return linkLabel;
	}
	public void setLinkLabel(String linkLabel) {
		this.linkLabel = linkLabel;
	}
	
	
}
