package model;

public class News {

	private String title;
	private String content;
	private String author;
	private String link;
	private String category;
	private String imgUrl;
	private String linkLabel;

	public String getLinkLabel() {
		return linkLabel;
	}

	public void setLinkLabel(String linkLabel) {
		this.linkLabel = linkLabel;
	}

	public News() {

	}

	public News(String title, String content, String author, String link) {
		if (title != null)
			this.title = title;
		if (content != null)
			this.content = content;
		if (author != null)
			this.author = author;
		if (link != null)
			this.link = link;
	}
	public News(String title, String content, String author, String link,String imgUrl) {
		if (title != null)
			this.title = title;
		if (content != null)
			this.content = content;
		if (author != null)
			this.author = author;
		if (link != null)
			this.link = link;
		if(imgUrl!=null)
			this.setImgUrl(imgUrl);
	}
	
	public News(String title, String content, String author, String link,String category,String imgUrl) {
		if (title != null)
			this.title = title;
		if (content != null)
			this.content = content;
		if (author != null)
			this.author = author;
		if (link != null)
			this.link = link;
		if(category!=null)
			this.category = category;
		if(imgUrl!=null)
			this.setImgUrl(imgUrl);
		
	}
	public News(String title, String content, String author, String link,String linkLabel,String category,String imgUrl) {
		if (title != null)
			this.title = title;
		if (content != null)
			this.content = content;
		if (author != null)
			this.author = author;
		if (link != null)
			this.link = link;
		if(category!=null)
			this.category = category;
		if(imgUrl!=null)
			this.setImgUrl(imgUrl);
		if(linkLabel!=null)
			this.linkLabel=linkLabel;
		
	}

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "News [title=" + title + ", content=" + content + ", author="
				+ author + ", link=" + link + "]";
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
