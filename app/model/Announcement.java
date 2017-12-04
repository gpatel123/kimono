package model;

import java.util.List;

public class Announcement {

	private String name;
	private List<News> newsList;

	public Announcement() {

	}

	public Announcement(String name, List<News> newsList) {
		if (name != null)
			this.name = name;
		if (newsList != null)
			this.newsList = newsList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

}
