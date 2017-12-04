package beans.content;

import java.util.List;

public class Item {
	private Sys_ sys;
	private String displayField;
	private String name;
	private String description;
	private List<Field> fields = null;
	

	public Sys_ getSys() {
		return sys;
	}

	public void setSys(Sys_ sys) {
		this.sys = sys;
	}

	public String getDisplayField() {
		return displayField;
	}

	public void setDisplayField(String displayField) {
		this.displayField = displayField;
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

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
