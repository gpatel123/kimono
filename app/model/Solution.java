package model;

public class Solution {
	private String title;
	private String content;
	private String iconUrl;

	public Solution() {

	}

	public Solution(String title, String content, String iconUrl) {
		if (title != null)
			this.title = title;
		if (content != null)
			this.content = content;
		if (iconUrl != null)
			this.iconUrl = iconUrl;
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

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

}
