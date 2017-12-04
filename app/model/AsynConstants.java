package model;
import play.Play;

public class AsynConstants {

	public static final String BASE_URL = Play.application().configuration().getString("baseUrl");
	public static final String entries = Play.application().configuration().getString("entries");
	public static final String assets = Play.application().configuration().getString("assets");
	public static final String spaceId = Play.application().configuration().getString("spaceId");
	public static final String access_token = Play.application().configuration().getString("access_token");
}
