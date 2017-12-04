package beans.entry;

import java.util.List;

public class Fields {
	private String headlineText;
	private String headlineContent;
	private HeaderImage headerImage;
	private String buttonLink;
    private String listDetails;
    private ListImage listImage;
    private List<ListItem> listItems1 = null;
    
    
	public ListImage getListImage() {
		return listImage;
	}

	public void setListImage(ListImage listImage) {
		this.listImage = listImage;
	}

	public List<ListItem> getListItems1() {
		return listItems1;
	}

	public void setListItems1(List<ListItem> listItems1) {
		this.listItems1 = listItems1;
	}

	public String getListDetails() {
		return listDetails;
	}

	public void setListDetails(String listDetails) {
		this.listDetails = listDetails;
	}

	public String getHeadlineText() {
		return headlineText;
	}

	public void setHeadlineText(String headlineText) {
		this.headlineText = headlineText;
	}

	public String getHeadlineContent() {
		return headlineContent;
	}

	public void setHeadlineContent(String headlineContent) {
		this.headlineContent = headlineContent;
	}

	public HeaderImage getHeaderImage() {
		return headerImage;
	}

	public void setHeaderImage(HeaderImage headerImage) {
		this.headerImage = headerImage;
	}

	public String getButtonLink() {
		return buttonLink;
	}

	public void setButtonLink(String buttonLink) {
		this.buttonLink = buttonLink;
	}

	private String solutionsTitle;
	private String solutionsDescription;
	private SolutionsIcon solutionsIcon;

	private SolutionsIcon icon;

	public String getSolutionsTitle() {
		return solutionsTitle;
	}

	public void setSolutionsTitle(String solutionsTitle) {
		this.solutionsTitle = solutionsTitle;
	}

	public String getSolutionsDescription() {
		return solutionsDescription;
	}

	public void setSolutionsDescription(String solutionsDescription) {
		this.solutionsDescription = solutionsDescription;
	}

	public SolutionsIcon getSolutionsIcon() {
		return solutionsIcon;
	}

	public void setSolutionsIcon(SolutionsIcon solutionsIcon) {
		this.solutionsIcon = solutionsIcon;
	}

	private String title;
	private String company;
	private String content;
	private Image image;

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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public SolutionsIcon getIcon() {
		return icon;
	}

	public void setIcon(SolutionsIcon icon) {
		this.icon = icon;
	}

	private String listName;
	private List<ListItem> listItems = null;

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public List<ListItem> getListItems() {
		return listItems;
	}

	public void setListItems(List<ListItem> listItems) {
		this.listItems = listItems;
	}

	private String author;
	private String link;
	private String linkLabel;
	
	public String getLinkLabel() {
		return linkLabel;
	}

	public void setLinkLabel(String linkLabel) {
		this.linkLabel = linkLabel;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	private String areaName;

	private String category;
	private HeaderImage mainImage;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public HeaderImage getMainImage() {
		return mainImage;
	}

	public void setMainImage(HeaderImage mainImage) {
		this.mainImage = mainImage;
	}
	
	private String headline;

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	private String pageName;
	private String pageContent;
	private String slug;
	
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
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
	
	


}


/*package beans.entry;

import java.util.List;

public class Fields {

	private String listName2;
	private List<ListName> listName = null;
	private String slug;
	private String pageName;
	private String pageContent;
	private String title;
	private String content;
	private SolutionsIcon icon;
	private String areaName;
	private List<ListItem> listItems = null;
	private HeaderImage mainImage;
	private String headline;
	private String author;
	private String category;
	private String link;
	private String linkLabel;
	private String headlineText;
	private String headlineContent;
	private HeaderImage headerImage;
	private String buttonLink;
	private Image image;
	private String company;
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getListName2() {
	return listName2;
	}

	public void setListName2(String listName2) {
	this.listName2 = listName2;
	}

	public List<ListName> getListName() {
	return listName;
	}

	public void setListName(List<ListName> listName) {
	this.listName = listName;
	}

	public String getSlug() {
	return slug;
	}

	public void setSlug(String slug) {
	this.slug = slug;
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

	public SolutionsIcon getIcon() {
	return icon;
	}

	public void setIcon(SolutionsIcon icon) {
	this.icon = icon;
	}

	public String getAreaName() {
	return areaName;
	}

	public void setAreaName(String areaName) {
	this.areaName = areaName;
	}

	public List<ListItem> getListItems() {
	return listItems;
	}

	public void setListItems(List<ListItem> listItems) {
	this.listItems = listItems;
	}

	public HeaderImage getMainImage() {
	return mainImage;
	}mainImage

	public void setMainImage(HeaderImage mainImage) {
	this.mainImage = mainImage;
	}

	public String getHeadline() {
	return headline;
	}

	public void setHeadline(String headline) {
	this.headline = headline;
	}

	public String getAuthor() {
	return author;
	}

	public void setAuthor(String author) {
	this.author = author;
	}

	public String getCategory() {
	return category;
	}

	public void setCategory(String category) {
	this.category = category;
	}

	public String getLink() {
	return link;
	}

	public void setLink(String link) {
	this.link = link;
	}

	public String getLinkLabel() {
	return linkLabel;
	}

	public void setLinkLabel(String linkLabel) {
	this.linkLabel = linkLabel;
	}

	public String getHeadlineText() {
	return headlineText;
	}

	public void setHeadlineText(String headlineText) {
	this.headlineText = headlineText;
	}

	public String getHeadlineContent() {
	return headlineContent;
	}

	public void setHeadlineContent(String headlineContent) {
	this.headlineContent = headlineContent;
	}

	public HeaderImage getHeaderImage() {
	return headerImage;
	}

	public void setHeaderImage(HeaderImage headerImage) {
	this.headerImage = headerImage;
	}

	public String getButtonLink() {
	return buttonLink;
	}

	public void setButtonLink(String buttonLink) {
	this.buttonLink = buttonLink;
	}
}
*/