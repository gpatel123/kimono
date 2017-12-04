package beans;

public class APIResult {
	@Override
	public String toString() {
		return "APIResult [userid=" + userid + ", alias=" + alias + ", name="
				+ name + ", surname=" + surname + ", url=" + url
				+ ", autologin=" + autologin + ", autologout=" + autologout
				+ ", lang=" + lang + ", refresh=" + refresh + ", type=" + type
				+ ", theme=" + theme + ", attemptFailed=" + attemptFailed
				+ ", attemptIp=" + attemptIp + ", attemptClock=" + attemptClock
				+ ", rowsPerPage=" + rowsPerPage + ", debugMode=" + debugMode
				+ ", userip=" + userip + ", sessionid=" + sessionid
				+ ", guiAccess=" + guiAccess + "]";
	}

	private String userid;
	private String alias;
	private String name;
	private String surname;
	private String url;
	private String autologin;
	private String autologout;
	private String lang;
	private String refresh;
	private String type;
	private String theme;
	private String attemptFailed;
	private String attemptIp;
	private String attemptClock;
	private String rowsPerPage;
	private Boolean debugMode;
	private String userip;
	private String sessionid;
	private String guiAccess;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAutologin() {
		return autologin;
	}

	public void setAutologin(String autologin) {
		this.autologin = autologin;
	}

	public String getAutologout() {
		return autologout;
	}

	public void setAutologout(String autologout) {
		this.autologout = autologout;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getAttemptFailed() {
		return attemptFailed;
	}

	public void setAttemptFailed(String attemptFailed) {
		this.attemptFailed = attemptFailed;
	}

	public String getAttemptIp() {
		return attemptIp;
	}

	public void setAttemptIp(String attemptIp) {
		this.attemptIp = attemptIp;
	}

	public String getAttemptClock() {
		return attemptClock;
	}

	public void setAttemptClock(String attemptClock) {
		this.attemptClock = attemptClock;
	}

	public String getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(String rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public Boolean getDebugMode() {
		return debugMode;
	}

	public void setDebugMode(Boolean debugMode) {
		this.debugMode = debugMode;
	}

	public String getUserip() {
		return userip;
	}

	public void setUserip(String userip) {
		this.userip = userip;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getGuiAccess() {
		return guiAccess;
	}

	public void setGuiAccess(String guiAccess) {
		this.guiAccess = guiAccess;
	}
}
