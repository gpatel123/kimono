package model;

public class DynamicPage {
	
	private String slug;
	private  String pageName;
	private String pageContent;
	private String imageLogo;
	private String pageContext2;
	
	public DynamicPage() {
		// TODO Auto-generated constructor stub
	}
	
	public DynamicPage(String slug,String pageName,String pageContent){
		if (slug != null)
			this.slug = slug;
		if (pageName != null)
			this.pageName = pageName;
		if (pageContent != null)
			this.pageContent = pageContent;
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

	public String getImageLogo() {
		return imageLogo;
	}

	public void setImageLogo(String imageLogo) {
		this.imageLogo = imageLogo;
	}

	public String getPageContext2() {
		return pageContext2;
	}

	public void setPageContext2(String pageContext2) {
		this.pageContext2 = pageContext2;
	}
	
	

}
