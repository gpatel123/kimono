package beans;

public class APIResponse {

	private String adminAPIsessionId;
	private Boolean login;
	private String jsonrpc;
	private APIResult result;
	private Integer id;

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public APIResult getResult() {
		return result;
	}

	public void setResult(APIResult result) {
		this.result = result;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdminAPIsessionId() {
		return adminAPIsessionId;
	}

	public void setAdminAPIsessionId(String adminAPIsessionId) {
		this.adminAPIsessionId = adminAPIsessionId;
	}

	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}
}
