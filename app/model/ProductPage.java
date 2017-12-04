package model;

public class ProductPage {
	private String title;
	private String content;
	private String author;
	private String mainImage;
	private String category;

	public ProductPage(String title, String content, String author,
			String mainImage, String category) {
		if (title != null)
			this.title = title;
		if (content != null)
			this.content = content;
		if (author != null)
			this.author = author;
		if (mainImage != null)
			this.mainImage = mainImage;
		if (category != null)
			this.category = category;
	}

	public ProductPage() {

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

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
