package model;

public class HeaderPage {

	private String headerText;
	private String headerDescription;
	private String headerUrl;
	private String buttonClick;

	@Override
	public String toString() {
		return "HeaderPage [headerText=" + headerText + ", headerDescription="
				+ headerDescription + ", headerUrl=" + headerUrl
				+ ", buttonClick=" + buttonClick + "]";
	}

	public HeaderPage() {

	}

	public HeaderPage(String text, String description, String url, String button) {
		if (text != null)
			this.headerText = text;
		if (url != null)
			this.headerUrl = url;
		if (description != null)
			this.headerDescription = description;
		if (button != null)
			this.buttonClick = button;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public String getHeaderDescription() {
		return headerDescription;
	}

	public void setHeaderDescription(String headerDescription) {
		this.headerDescription = headerDescription;
	}

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

	public String getButtonClick() {
		return buttonClick;
	}

	public void setButtonClick(String buttonClick) {
		this.buttonClick = buttonClick;
	}

}
