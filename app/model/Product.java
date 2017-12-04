package model;

public class Product {

	private String name;
	private String description;
	private String url;
	
	private String title;
	private String content;
	private String link;
	private String imageUrl;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public Product(String title, String content, String link,
			String imageUrl) {
		if (title != null)
			this.title = title;
		if (content != null)
			this.content = content;
		if (link != null)
			this.link = link;
		if (imageUrl != null)
			this.imageUrl = imageUrl;

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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
