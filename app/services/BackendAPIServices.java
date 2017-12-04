package services;

import beans.APIRequest;

public interface BackendAPIServices {

	public StringBuffer callAPI(APIRequest apiRequest, String url);
	
}
