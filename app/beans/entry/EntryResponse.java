package beans.entry;

import java.util.List;

public class EntryResponse {
	private Sys sys;
	private Integer total;
	private Integer skip;
	private Integer limit;
	private List<Item> items = null;
	private Includes includes;

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getSkip() {
		return skip;
	}

	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "EntryResponse [sys=" + sys + ", total=" + total + ", skip=" + skip + ", limit=" + limit + ", items="
				+ items + ", includes=" + includes + "]";
	}

	public Includes getIncludes() {
		return includes;
	}

	public void setIncludes(Includes includes) {
		this.includes = includes;
	}
}
