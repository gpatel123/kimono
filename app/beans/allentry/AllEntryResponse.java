package beans.allentry;

import java.util.List;

import beans.entry.Includes;

public class AllEntryResponse {
	private AllSys sys;
	private Integer total;
	private Integer skip;
	private Integer limit;
	private List<AllItem> items = null;
	private Includes includes;

	public AllSys getSys() {
	return sys;
	}

	public void setSys(AllSys sys) {
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

	public List<AllItem> getItems() {
	return items;
	}

	public void setItems(List<AllItem> items) {
	this.items = items;
	}

	public Includes getIncludes() {
	return includes;
	}

	public void setIncludes(Includes includes) {
	this.includes = includes;
	}

}
