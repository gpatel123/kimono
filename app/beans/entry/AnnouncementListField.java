package beans.entry;

import java.util.List;

public class AnnouncementListField {
	private String listName2;
	private List<ListItem> listName = null;
	private String slug;
	private String pageName;
	private String pageContent;
	
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getListName2() {
		return listName2;
	}

	public void setListName2(String listName2) {
		this.listName2 = listName2;
	}

	public List<ListItem> getListName() {
		return listName;
	}

	public void setListName(List<ListItem> listName) {
		this.listName = listName;
	}

	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

}
