package beans.allentry;

import java.util.List;

public class AllFields {

private String listName2;
private Object listName = null;
private String title;
private String company;
private String content;
private AllImage image;
private String slug;
private String pageName;
private String pageContent;
private AllIcon icon;
private String headlineText;
private String headlineContent;
private AllHeaderImage headerImage;
private String buttonLink;
private String areaName;
private List<AllListItem> listItems = null;
private AllMainImage mainImage;
private String headline;
private String author;
private String category;
private String link;
private String linkLabel;
private String strlistName;
private List<AllListName>  arrlistName;


public String getStrlistName() {
	return strlistName;
}

public void setStrlistName(String strlistName) {
	this.strlistName = strlistName;
}

public List<AllListName> getArrlistName() {
	return arrlistName;
}

public void setArrlistName(List<AllListName> arrlistName) {
	this.arrlistName = arrlistName;
}

public String getListName2() {
return listName2;
}

public void setListName2(String listName2) {
this.listName2 = listName2;
}

public Object getListName() {
	
return listName;
}

public void setListName(Object listName) {
	if(listName instanceof String){
		this.strlistName=String.valueOf(listName);
	}else{
		this.arrlistName=(List<AllListName>) listName;
	}
this.listName = listName;
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

public AllImage getImage() {
return image;
}

public void setImage(AllImage image) {
this.image = image;
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

public AllIcon getIcon() {
return icon;
}

public void setIcon(AllIcon icon) {
this.icon = icon;
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

public AllHeaderImage getHeaderImage() {
return headerImage;
}

public void setHeaderImage(AllHeaderImage headerImage) {
this.headerImage = headerImage;
}

public String getButtonLink() {
return buttonLink;
}

public void setButtonLink(String buttonLink) {
this.buttonLink = buttonLink;
}

public String getAreaName() {
return areaName;
}

public void setAreaName(String areaName) {
this.areaName = areaName;
}

public List<AllListItem> getListItems() {
return listItems;
}

public void setListItems(List<AllListItem> listItems) {
this.listItems = listItems;
}

public AllMainImage getMainImage() {
return mainImage;
}

public void setMainImage(AllMainImage mainImage) {
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



}
