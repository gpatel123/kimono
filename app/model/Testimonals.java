package model;

public class Testimonals {

	private String title;
	private String company;
	private String content;
	private String imageUrl;

	public Testimonals() {

	}

	public Testimonals(String title, String company, String content,
			String imageUrl) {
		if (title != null)
			this.title = title;
		if (company != null)
			this.company = company;
		if (content != null)
			this.content = content;
		if (imageUrl != null)
			this.imageUrl = imageUrl;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
