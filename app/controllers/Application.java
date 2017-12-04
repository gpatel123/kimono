package controllers;

import static play.data.Form.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Announcement;
import model.AsynConstants;
import model.DynamicPage;
import model.HeaderPage;
import model.News;
import model.PageContent;
import model.Product;
import model.ProductPage;
import model.Resource;
import model.Solution;
import model.Testimonals;
import model.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Session;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Http.RequestHeader;
import services.BackendAPIServicesImpl;
import views.html.*;
import beans.APIRequest;
import beans.APIResponse;
import beans.Params;
import beans.allentry.AllFields;
import beans.allentry.AllItem;
import beans.assets.images.AssetImageResponse;
import beans.content.ContentResponse;
import beans.content.Field;
import beans.content.Item;
import beans.content.Items;
import beans.entry.AnnouncementListItem;
import beans.entry.AnnouncementListResponse;
import beans.entry.Asset;
import beans.entry.EntryResponse;
import beans.entry.Fields;
import beans.entry.Image;
import beans.entry.Includes;
import beans.entry.ListItem;
import beans.entry.SingleEntryResponse;
import flexjson.JSON;
import jdk.nashorn.internal.ir.ObjectNode;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

public class Application extends Controller {

	static Form<User> userform = form(User.class);

	public static Result index() {

		// String
		// url="https://cdn.contentful.com/spaces/j6kwqi43xct3/content_types?access_token=6be863b7ca36c67af0e01a22c82a3977a646bbc999b91b741ec82c93f3cc345a";
		String url = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/content_types?access_token="
				+ AsynConstants.access_token;
		StringBuffer loginResponse = BackendAPIServicesImpl.callGetAPI(url);
		// System.out.println("---------------"+loginResponse);
		ContentResponse apiResponse = new Gson().fromJson(loginResponse.toString(), ContentResponse.class);

		for (Item item : apiResponse.getItems()) {
			System.out.println("content Id:" + item.getSys().getId() + "\tname: " + item.getName());
			session().put(item.getSys().getId(), item.getSys().getId());
		}

		String urlHeader = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("heroBanner")
				+ "&include=10";

		StringBuffer headerResponse = BackendAPIServicesImpl.callGetAPI(urlHeader);
		EntryResponse entryResponse = new Gson().fromJson(headerResponse.toString(), EntryResponse.class);

		List<HeaderPage> headerPageList = new ArrayList<HeaderPage>();

		for (beans.entry.Item item : entryResponse.getItems()) {
			String imgUrl = null;

			String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.assets + "/"
					+ item.getFields().getHeaderImage().getSys().getId() + "?access_token="
					+ AsynConstants.access_token;
			StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

			AssetImageResponse assetImageResponse = new Gson().fromJson(imageResponse.toString(),
					AssetImageResponse.class);
			if (assetImageResponse != null) {
				imgUrl = assetImageResponse.getFields().getFile().getUrl();
			}
			HeaderPage headerPage;
			if (item.getFields().getButtonLink() == null) {
				headerPage = new HeaderPage(item.getFields().getHeadlineText(), item.getFields().getHeadlineContent(),
						imgUrl, null);
			} else {
				headerPage = new HeaderPage(item.getFields().getHeadlineText(), item.getFields().getHeadlineContent(),
						imgUrl, item.getFields().getButtonLink());
			}
			headerPageList.add(headerPage);
		}

		/*
		 * HeaderPage hp = new HeaderPage("demo text", "demo description",
		 * "https://www.w3schools.com/css/trolltunga.jpg", null);
		 * headerPageList.add(hp);
		 * 
		 * HeaderPage header = new HeaderPage("demo text123",
		 * "demo description123",
		 * "https://static.pexels.com/photos/248797/pexels-photo-248797.jpeg",
		 * null); headerPageList.add(header);
		 */

		String basicContentUrl = "https://cdn.contentful.com/spaces/j6kwqi43xct3/entries?access_token=6be863b7ca36c67af0e01a22c82a3977a646bbc999b91b741ec82c93f3cc345a&content_type="
				+ session().get("testimonialBlock");

		String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("testimonialBlock");
		StringBuffer basicResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseBasic = new Gson().fromJson(basicResponse.toString(), EntryResponse.class);

		StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
				EntryResponse.class);

		List<Testimonals> testimonals = new ArrayList<Testimonals>();
		for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

			if (item.getFields().getAreaName() != null) {
				if (item.getFields().getAreaName().equals("homepage testimonials")) {
					for (ListItem listItem : item.getFields().getListItems()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String imgUrl = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
										Testimonals testimonal = new Testimonals(
												singleEntryResponse.getFields().getTitle(),
												singleEntryResponse.getFields().getCompany(),
												singleEntryResponse.getFields().getContent(), imgUrl);
										testimonals.add(testimonal);
									}
								}
							}
						}
					}
				}
			}
		}

		String urlSolution = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("solutionList");

		StringBuffer solutionResponse = BackendAPIServicesImpl.callGetAPI(urlSolution);

		EntryResponse entryResponseSolution = new Gson().fromJson(solutionResponse.toString(), EntryResponse.class);
		List<Solution> solutions = new ArrayList<Solution>();
		for (beans.entry.Item item : entryResponseSolution.getItems()) {

			if (item.getFields().getListName().equals("homepage solutions")) {
				for (ListItem listName : item.getFields().getListItems()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPIGET(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String imgUrl = null;
						if (singleEntryResponse != null) {
							String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
									+ AsynConstants.assets + "/"
									+ singleEntryResponse.getFields().getIcon().getSys().getId() + "?access_token="
									+ AsynConstants.access_token;
							StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
							AssetImageResponse assetImageResponse = new Gson().fromJson(imageResponse.toString(),
									AssetImageResponse.class);
							if (assetImageResponse != null) {
								if (assetImageResponse.getFields() != null) {
									if (assetImageResponse.getFields().getFile() != null) {
										if (assetImageResponse.getFields().getFile().getUrl() != null) {
											imgUrl = assetImageResponse.getFields().getFile().getUrl();
										}
									}

								}
							}
							Solution solution = new Solution(singleEntryResponse.getFields().getTitle(),
									singleEntryResponse.getFields().getContent(), imgUrl);
							solutions.add(solution);
						}

					}
				}

			}

		}

		return ok(home.render(headerPageList, testimonals, solutions));
	}

	public static Result marketplace() {
		String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("announcementList");
		StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
		AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
				AnnouncementListResponse.class);
		List<ProductPage> productPageList = new ArrayList<ProductPage>();
		Set<String> categories = new HashSet<String>();
		for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
			if (item.getFields().getListName2().equals("products page list")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String category = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getCategory() != null)
									category = singleEntryResponse.getFields().getCategory();
								String imgUrl = null;
								if (singleEntryResponse.getFields().getMainImage() != null) {
									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();
												}
											}
										}
									}
								}
								ProductPage productPage = new ProductPage(title, content, author, imgUrl, category);

								categories.add(category);
								productPageList.add(productPage);
							}
						}

					}
				}
			}
		}

		return ok(marketplace.render(productPageList, categories));
	}

	public static Result callsearchProductPage() {
		List<ProductPage> productPageList = new ArrayList<ProductPage>();

		Form<User> requestData = userform.bindFromRequest();
		User user = requestData.get();
		String username = user.getUsername();
		if (username.equals("ALL")) {
			String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
					+ "?access_token=" + AsynConstants.access_token + "&content_type="
					+ session().get("announcementList");
			StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
			AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
					AnnouncementListResponse.class);
			for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
				if (item.getFields().getListName2().equals("products page list")) {
					for (ListItem listName : item.getFields().getListName()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String title = null;
							String content = null;
							String author = null;
							String category = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getTitle() != null)
										title = singleEntryResponse.getFields().getTitle();
									if (singleEntryResponse.getFields().getContent() != null)
										content = singleEntryResponse.getFields().getContent();
									if (singleEntryResponse.getFields().getAuthor() != null)
										author = singleEntryResponse.getFields().getAuthor();
									if (singleEntryResponse.getFields().getCategory() != null)
										category = singleEntryResponse.getFields().getCategory();
									String imgUrl = null;
									if (singleEntryResponse.getFields().getMainImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getMainImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									ProductPage productPage = new ProductPage(title, content, author, imgUrl, category);
									productPageList.add(productPage);
								}
							}
						}
					}
				}
			}
		} else {
			username = username.replace(" ", "%20");
			String urlSearchProduct = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
					+ "?access_token=" + AsynConstants.access_token + "&content_type=basicContent&fields.category="
					+ username;
			StringBuffer response = BackendAPIServicesImpl.callGetAPI(urlSearchProduct);
			EntryResponse entryResponseSolution = new Gson().fromJson(response.toString(), EntryResponse.class);
			for (beans.entry.Item item : entryResponseSolution.getItems()) {

				String title = null;
				String content = null;
				String author = null;
				String category = null;
				if (item.getFields().getTitle() != null)
					title = item.getFields().getTitle();
				if (item.getFields().getContent() != null)
					content = item.getFields().getContent();
				if (item.getFields().getAuthor() != null)
					author = item.getFields().getAuthor();
				if (item.getFields().getCategory() != null)
					category = item.getFields().getCategory();
				String imgUrl = null;
				if (item.getFields().getMainImage() != null) {
					String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.assets
							+ "/" + item.getFields().getMainImage().getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
					AssetImageResponse assetImageResponse = new Gson().fromJson(imageResponse.toString(),
							AssetImageResponse.class);
					if (assetImageResponse != null) {
						if (assetImageResponse.getFields() != null) {
							if (assetImageResponse.getFields().getFile() != null) {
								if (assetImageResponse.getFields().getFile().getUrl() != null) {
									imgUrl = assetImageResponse.getFields().getFile().getUrl();
								}
							}
						}
					}
				}
				ProductPage productPage = new ProductPage(title, content, author, imgUrl, category);

				productPageList.add(productPage);

			}
		}

		return ok(Json.toJson(productPageList));
	}

	public static Result newsevents() {
		if (session().get("announcementList") != null) {
			String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
					+ "?access_token=" + AsynConstants.access_token + "&content_type="
					+ session().get("announcementList");
			StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
			AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
					AnnouncementListResponse.class);
			List<News> newsList = new ArrayList<News>();
			for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
				if (item.getFields().getListName2().equals("News Events List")) {
					for (ListItem listName : item.getFields().getListName()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String title = null;
							String content = null;
							String author = null;
							String category = null;
							String link = null;
							String imgUrl = null;
							String linkLabel = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getTitle() != null)
										title = singleEntryResponse.getFields().getTitle();
									if (singleEntryResponse.getFields().getContent() != null)
										content = singleEntryResponse.getFields().getContent();
									if (singleEntryResponse.getFields().getAuthor() != null)
										author = singleEntryResponse.getFields().getAuthor();
									if (singleEntryResponse.getFields().getCategory() != null)
										category = singleEntryResponse.getFields().getCategory();
									if (singleEntryResponse.getFields().getLink() != null)
										link = singleEntryResponse.getFields().getLink();
									if (singleEntryResponse.getFields().getLinkLabel() != null)
										linkLabel = singleEntryResponse.getFields().getLinkLabel();

									if (singleEntryResponse.getFields().getMainImage() != null) {
										if (singleEntryResponse.getFields().getMainImage().getSys() != null) {
											String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId
													+ "/" + AsynConstants.assets + "/"
													+ singleEntryResponse.getFields().getMainImage().getSys().getId()
													+ "?access_token=" + AsynConstants.access_token;
											StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
											AssetImageResponse assetImageResponse = new Gson()
													.fromJson(imageResponse.toString(), AssetImageResponse.class);
											if (assetImageResponse != null) {
												if (assetImageResponse.getFields() != null) {
													if (assetImageResponse.getFields().getFile() != null) {
														if (assetImageResponse.getFields().getFile().getUrl() != null) {
															imgUrl = assetImageResponse.getFields().getFile().getUrl();
														}
													}
												}
											}
										}
									}
									News news = new News(title, content, author, link, linkLabel, category, imgUrl);
									newsList.add(news);
								}
							}

						}
					}
				}
			}
			return ok(newsevents.render(newsList));
		} else {
			return ok(newsevents.render(null));
		}
	}

	public static Result getProducts() {
		return ok(products.render());
	}

	public static Result getPageContent() {
		if (session().get("pageContent") != null) {
			String pageContentUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
					+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("pageContent");

			StringBuffer pageContentResponse = BackendAPIServicesImpl.callGetAPI(pageContentUrl);

			EntryResponse entryResponsePageContent = new Gson().fromJson(pageContentResponse.toString(),
					EntryResponse.class);

			PageContent page = new PageContent();
			for (int i = 0; i < entryResponsePageContent.getItems().size(); i++) {
				if (entryResponsePageContent.getItems().get(0) != null) {
					if (entryResponsePageContent.getItems().get(0).getFields() != null) {
						if (entryResponsePageContent.getItems().get(0).getFields().getHeadline() != null)
							page.setHeadline(entryResponsePageContent.getItems().get(0).getFields().getHeadline());
						if (entryResponsePageContent.getItems().get(0).getFields().getContent() != null)
							page.setContent(entryResponsePageContent.getItems().get(0).getFields().getContent());
					}
				}
			}

			System.out.println("pageContent-----:" + page.getHeadline() + "\n" + page.getContent());

			return ok(contact_us.render(page));
		} else {
			return ok(contact_us.render(null));
		}
	}

	public static Result getDynamicPage() {
		if (session().get("dynamicPage") != null) {
			String dynamicPageUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
					+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("dynamicPage");
			StringBuffer dynamicPageResponse = BackendAPIServicesImpl.callGetAPI(dynamicPageUrl);

			EntryResponse entryResponseDynamicPage = new Gson().fromJson(dynamicPageResponse.toString(),
					EntryResponse.class);

			DynamicPage dynamicPage = new DynamicPage();

			for (int i = 0; i < entryResponseDynamicPage.getItems().size(); i++) {

				if (entryResponseDynamicPage.getItems().get(i) != null) {
					if (entryResponseDynamicPage.getItems().get(i).getFields() != null) {
						System.out.println("entryResponseDynamicPage.getItems().get(0).getFields()==="
								+ entryResponseDynamicPage.getItems().get(i).getFields().getPageContent());
						if (entryResponseDynamicPage.getItems().get(i).getFields().getPageName() != null)
							dynamicPage
									.setPageName(entryResponseDynamicPage.getItems().get(i).getFields().getPageName());
						if (entryResponseDynamicPage.getItems().get(i).getFields().getPageContent() != null)
							dynamicPage.setPageContent(
									entryResponseDynamicPage.getItems().get(i).getFields().getPageContent());
						if (entryResponseDynamicPage.getItems().get(i).getFields().getSlug() != null)
							dynamicPage.setSlug(entryResponseDynamicPage.getItems().get(i).getFields().getSlug());
					}
				}
			}
			System.out.println("dynamicPage slug :::::" + dynamicPage.getPageContent());
			return ok(dynamic_page.render(dynamicPage));
		} else {
			return ok(dynamic_page.render(null));
		}
	}

	public static Result getAudience() {
		if (session().get("announcementList") != null) {
			String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
					+ "?access_token=" + AsynConstants.access_token + "&content_type="
					+ session().get("announcementList");
			StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
			AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
					AnnouncementListResponse.class);
			List<News> solutionList = new ArrayList<News>();
			List<News> announcementList = new ArrayList<News>();
			for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
				if (item.getFields().getListName2().equals("Comprehensive Solutions List")) {
					for (ListItem listName : item.getFields().getListName()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String title = null;
							String content = null;
							String author = null;
							String link = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getTitle() != null)
										title = singleEntryResponse.getFields().getTitle();
									if (singleEntryResponse.getFields().getContent() != null)
										content = singleEntryResponse.getFields().getContent();
									if (singleEntryResponse.getFields().getAuthor() != null)
										author = singleEntryResponse.getFields().getAuthor();
									if (singleEntryResponse.getFields().getLink() != null)
										link = singleEntryResponse.getFields().getLink();

									String imgUrl = null;

									if (singleEntryResponse.getFields().getMainImage() != null) {

										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getMainImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									News news = new News(title, content, author, link, imgUrl);
									solutionList.add(news);
								}
							}
						}
					}
				} else if (item.getFields().getListName2().equals("Announcement List")) {
					for (ListItem listName : item.getFields().getListName()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String title = null;
							String content = null;
							String author = null;
							String link = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getTitle() != null)
										title = singleEntryResponse.getFields().getTitle();
									if (singleEntryResponse.getFields().getContent() != null)
										content = singleEntryResponse.getFields().getContent();
									if (singleEntryResponse.getFields().getAuthor() != null)
										author = singleEntryResponse.getFields().getAuthor();
									if (singleEntryResponse.getFields().getLink() != null)
										link = singleEntryResponse.getFields().getLink();

									String imgUrl = null;
									if (singleEntryResponse.getFields().getMainImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getMainImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();

													}
												}
											}
										}
									}
									News news = new News(title, content, author, link, imgUrl);
									announcementList.add(news);
								}
							}
						}
					}
				}
			}

			String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
					+ "?access_token=" + AsynConstants.access_token + "&content_type="
					+ session().get("testimonialBlock");

			StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
			EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
					EntryResponse.class);

			List<Testimonals> testimonals = new ArrayList<Testimonals>();
			for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

				if (item.getFields().getAreaName() != null) {
					if (item.getFields().getAreaName().equals("homepage testimonials")) {
						for (ListItem listItem : item.getFields().getListItems()) {
							String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
									+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
									+ AsynConstants.access_token;
							StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
							if (entrySingleResponse != null) {
								SingleEntryResponse singleEntryResponse = new Gson()
										.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
								String imgUrl = null;
								if (singleEntryResponse != null) {
									if (singleEntryResponse.getFields() != null) {
										if (singleEntryResponse.getFields().getImage() != null) {

											String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId
													+ "/" + AsynConstants.assets + "/"
													+ singleEntryResponse.getFields().getImage().getSys().getId()
													+ "?access_token=" + AsynConstants.access_token;
											StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
											AssetImageResponse assetImageResponse = new Gson()
													.fromJson(imageResponse.toString(), AssetImageResponse.class);

											if (assetImageResponse != null) {
												if (assetImageResponse.getFields() != null) {
													if (assetImageResponse.getFields().getFile() != null) {
														if (assetImageResponse.getFields().getFile().getUrl() != null) {
															imgUrl = assetImageResponse.getFields().getFile().getUrl();
														}
													}
												}
											}
										}
										Testimonals testimonal = new Testimonals(
												singleEntryResponse.getFields().getTitle(),
												singleEntryResponse.getFields().getCompany(),
												singleEntryResponse.getFields().getContent(), imgUrl);
										testimonals.add(testimonal);
									}
								}

							}
						}
					}
				}
			}

			return ok(audience.render(solutionList, announcementList, testimonals, null));
		} else {
			return ok(audience.render(null, null, null, null));
		}
	}

	public static Result getK12() {
		String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("announcementList");
		StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
		AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
				AnnouncementListResponse.class);
		List<News> solutionList = new ArrayList<News>();
		List<News> announcementList = new ArrayList<News>();
		for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
			if (item.getFields().getListName2().equals("K12 Solutions List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);

					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {

									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();
												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								solutionList.add(news);
							}
						}
					}
				}
			} else if (item.getFields().getListName2().equals("K12 Announcements")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {
									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();

												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								announcementList.add(news);
							}
						}
					}
				}
			}
		}

		String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("testimonialBlock");

		StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
				EntryResponse.class);

		List<Testimonals> testimonals = new ArrayList<Testimonals>();
		for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

			if (item.getFields().getAreaName() != null) {
				if (item.getFields().getAreaName().equals("homepage testimonials")) {
					for (ListItem listItem : item.getFields().getListItems()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String imgUrl = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									Testimonals testimonal = new Testimonals(singleEntryResponse.getFields().getTitle(),
											singleEntryResponse.getFields().getCompany(),
											singleEntryResponse.getFields().getContent(), imgUrl);
									testimonals.add(testimonal);
								}
							}

						}
					}
				}
			}
		}
		return ok(k12.render(solutionList, announcementList, testimonals, null));
	}

	public static Result getHigher_education() {

		String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("announcementList");
		StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
		AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
				AnnouncementListResponse.class);
		List<News> solutionList = new ArrayList<News>();
		List<News> announcementList = new ArrayList<News>();
		for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
			if (item.getFields().getListName2().equals("Higher Ed Solutions List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);

					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {

									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();
												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								solutionList.add(news);
							}
						}
					}
				}
			} else if (item.getFields().getListName2().equals("Higher Ed Announcements List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {
									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();

												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								announcementList.add(news);
							}
						}
					}
				}
			}
		}

		String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("testimonialBlock");

		StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
				EntryResponse.class);

		List<Testimonals> testimonals = new ArrayList<Testimonals>();
		for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

			if (item.getFields().getAreaName() != null) {
				if (item.getFields().getAreaName().equals("homepage testimonials")) {
					for (ListItem listItem : item.getFields().getListItems()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String imgUrl = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									Testimonals testimonal = new Testimonals(singleEntryResponse.getFields().getTitle(),
											singleEntryResponse.getFields().getCompany(),
											singleEntryResponse.getFields().getContent(), imgUrl);
									testimonals.add(testimonal);
								}
							}

						}
					}
				}
			}
		}

		return ok(higher_education.render(solutionList, announcementList, testimonals, null));
	}

	public static Result getPartners() {

		String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("announcementList");
		StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
		AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
				AnnouncementListResponse.class);
		List<News> solutionList = new ArrayList<News>();
		List<News> announcementList = new ArrayList<News>();
		for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
			if (item.getFields().getListName2().equals("Partners Solutions List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);

					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {

									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();
												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								solutionList.add(news);
							}
						}
					}
				}
			} else if (item.getFields().getListName2().equals("Partner Announcements List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {
									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();

												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								announcementList.add(news);
							}
						}
					}
				}
			}
		}

		String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("testimonialBlock");

		StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
				EntryResponse.class);

		List<Testimonals> testimonals = new ArrayList<Testimonals>();
		for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

			if (item.getFields().getAreaName() != null) {
				if (item.getFields().getAreaName().equals("homepage testimonials")) {
					for (ListItem listItem : item.getFields().getListItems()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String imgUrl = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									Testimonals testimonal = new Testimonals(singleEntryResponse.getFields().getTitle(),
											singleEntryResponse.getFields().getCompany(),
											singleEntryResponse.getFields().getContent(), imgUrl);
									testimonals.add(testimonal);
								}
							}

						}
					}
				}
			}
		}

		return ok(partners.render(solutionList, announcementList, testimonals, null));
	}

	public static Result getDevelopers() {
		String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("announcementList");
		StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
		AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
				AnnouncementListResponse.class);
		List<News> solutionList = new ArrayList<News>();
		List<News> announcementList = new ArrayList<News>();
		for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
			if (item.getFields().getListName2().equals("Developer Solutions List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);

					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {

									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();
												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								solutionList.add(news);
							}
						}
					}
				}
			} else if (item.getFields().getListName2().equals("Developer Announcements List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {
									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();

												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								announcementList.add(news);
							}
						}
					}
				}
			}
		}

		String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("testimonialBlock");

		StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
				EntryResponse.class);

		List<Testimonals> testimonals = new ArrayList<Testimonals>();
		for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

			if (item.getFields().getAreaName() != null) {
				if (item.getFields().getAreaName().equals("homepage testimonials")) {
					for (ListItem listItem : item.getFields().getListItems()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String imgUrl = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									Testimonals testimonal = new Testimonals(singleEntryResponse.getFields().getTitle(),
											singleEntryResponse.getFields().getCompany(),
											singleEntryResponse.getFields().getContent(), imgUrl);
									testimonals.add(testimonal);
								}
							}

						}
					}
				}
			}
		}

		return ok(developers.render(solutionList, announcementList, testimonals, null));
	}

	public static Result getK12Link(String subTitle) {
		String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("announcementList");
		StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
		AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
				AnnouncementListResponse.class);
		List<News> solutionList = new ArrayList<News>();
		List<News> announcementList = new ArrayList<News>();
		for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
			if (item.getFields().getListName2().equals("K12 Solutions List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);

					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {

									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();
												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								solutionList.add(news);
							}
						}
					}
				}
			} else if (item.getFields().getListName2().equals("K12 Announcements")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {
									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();

												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								announcementList.add(news);
							}
						}
					}
				}
			}
		}

		String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("testimonialBlock");

		StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
				EntryResponse.class);

		List<Testimonals> testimonals = new ArrayList<Testimonals>();
		for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

			if (item.getFields().getAreaName() != null) {
				if (item.getFields().getAreaName().equals("homepage testimonials")) {
					for (ListItem listItem : item.getFields().getListItems()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String imgUrl = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									Testimonals testimonal = new Testimonals(singleEntryResponse.getFields().getTitle(),
											singleEntryResponse.getFields().getCompany(),
											singleEntryResponse.getFields().getContent(), imgUrl);
									testimonals.add(testimonal);
								}
							}

						}
					}
				}
			}
		}
		return ok(k12.render(solutionList, announcementList, testimonals, subTitle));
	}

	public static Result getHigher_educationLink(String subTitle) {
		String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("announcementList");
		StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
		AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
				AnnouncementListResponse.class);
		List<News> solutionList = new ArrayList<News>();
		List<News> announcementList = new ArrayList<News>();
		for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
			if (item.getFields().getListName2().equals("Higher Ed Solutions List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);

					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {

									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();
												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								solutionList.add(news);
							}
						}
					}
				}
			} else if (item.getFields().getListName2().equals("Higher Ed Announcements List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {
									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();

												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								announcementList.add(news);
							}
						}
					}
				}
			}
		}

		String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("testimonialBlock");

		StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
				EntryResponse.class);

		List<Testimonals> testimonals = new ArrayList<Testimonals>();
		for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

			if (item.getFields().getAreaName() != null) {
				if (item.getFields().getAreaName().equals("homepage testimonials")) {
					for (ListItem listItem : item.getFields().getListItems()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String imgUrl = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									Testimonals testimonal = new Testimonals(singleEntryResponse.getFields().getTitle(),
											singleEntryResponse.getFields().getCompany(),
											singleEntryResponse.getFields().getContent(), imgUrl);
									testimonals.add(testimonal);
								}
							}

						}
					}
				}
			}
		}

		return ok(higher_education.render(solutionList, announcementList, testimonals, subTitle));
	}

	public static Result getPartnersLink(String subTitle) {
		String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("announcementList");
		StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
		AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
				AnnouncementListResponse.class);
		List<News> solutionList = new ArrayList<News>();
		List<News> announcementList = new ArrayList<News>();
		for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
			if (item.getFields().getListName2().equals("Partners Solutions List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);

					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {

									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();
												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								solutionList.add(news);
							}
						}
					}
				}
			} else if (item.getFields().getListName2().equals("Partner Announcements List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {
									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();

												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								announcementList.add(news);
							}
						}
					}
				}
			}
		}

		String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("testimonialBlock");

		StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
				EntryResponse.class);

		List<Testimonals> testimonals = new ArrayList<Testimonals>();
		for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

			if (item.getFields().getAreaName() != null) {
				if (item.getFields().getAreaName().equals("homepage testimonials")) {
					for (ListItem listItem : item.getFields().getListItems()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String imgUrl = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									Testimonals testimonal = new Testimonals(singleEntryResponse.getFields().getTitle(),
											singleEntryResponse.getFields().getCompany(),
											singleEntryResponse.getFields().getContent(), imgUrl);
									testimonals.add(testimonal);
								}
							}

						}
					}
				}
			}
		}

		return ok(partners.render(solutionList, announcementList, testimonals, subTitle));
	}

	public static Result getDevelopersLink(String subTitle) {
		String urlAnnounment = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("announcementList");
		StringBuffer audienceResponse = BackendAPIServicesImpl.callGetAPI(urlAnnounment);
		AnnouncementListResponse entryResponseSolution = new Gson().fromJson(audienceResponse.toString(),
				AnnouncementListResponse.class);
		List<News> solutionList = new ArrayList<News>();
		List<News> announcementList = new ArrayList<News>();
		for (beans.entry.AnnouncementListItem item : entryResponseSolution.getItems()) {
			if (item.getFields().getListName2().equals("Developer Solutions List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);

					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {

									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();
												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								solutionList.add(news);
							}
						}
					}
				}
			} else if (item.getFields().getListName2().equals("Developer Announcements List")) {
				for (ListItem listName : item.getFields().getListName()) {
					String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
							+ AsynConstants.entries + "/" + listName.getSys().getId() + "?access_token="
							+ AsynConstants.access_token;
					StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
					if (entrySingleResponse != null) {
						SingleEntryResponse singleEntryResponse = new Gson().fromJson(entrySingleResponse.toString(),
								SingleEntryResponse.class);
						String title = null;
						String content = null;
						String author = null;
						String link = null;
						if (singleEntryResponse != null) {
							if (singleEntryResponse.getFields() != null) {
								if (singleEntryResponse.getFields().getTitle() != null)
									title = singleEntryResponse.getFields().getTitle();
								if (singleEntryResponse.getFields().getContent() != null)
									content = singleEntryResponse.getFields().getContent();
								if (singleEntryResponse.getFields().getAuthor() != null)
									author = singleEntryResponse.getFields().getAuthor();
								if (singleEntryResponse.getFields().getLink() != null)
									link = singleEntryResponse.getFields().getLink();

								String imgUrl = null;

								if (singleEntryResponse.getFields().getMainImage() != null) {
									String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
											+ AsynConstants.assets + "/"
											+ singleEntryResponse.getFields().getMainImage().getSys().getId()
											+ "?access_token=" + AsynConstants.access_token;
									StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);

									AssetImageResponse assetImageResponse = new Gson()
											.fromJson(imageResponse.toString(), AssetImageResponse.class);
									if (assetImageResponse != null) {
										if (assetImageResponse.getFields() != null) {
											if (assetImageResponse.getFields().getFile() != null) {
												if (assetImageResponse.getFields().getFile().getUrl() != null) {
													imgUrl = assetImageResponse.getFields().getFile().getUrl();

												}
											}
										}
									}
								}

								News news = new News(title, content, author, link, imgUrl);
								announcementList.add(news);
							}
						}
					}
				}
			}
		}

		String urlTestimonial = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("testimonialBlock");
		
		StringBuffer testimonalResponse = BackendAPIServicesImpl.callGetAPI(urlTestimonial);
		EntryResponse entryResponseTestimonial = new Gson().fromJson(testimonalResponse.toString(),
				EntryResponse.class);

		List<Testimonals> testimonals = new ArrayList<Testimonals>();
		for (beans.entry.Item item : entryResponseTestimonial.getItems()) {

			if (item.getFields().getAreaName() != null) {
				if (item.getFields().getAreaName().equals("homepage testimonials")) {
					for (ListItem listItem : item.getFields().getListItems()) {
						String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
								+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
								+ AsynConstants.access_token;
						StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
						if (entrySingleResponse != null) {
							SingleEntryResponse singleEntryResponse = new Gson()
									.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
							String imgUrl = null;
							if (singleEntryResponse != null) {
								if (singleEntryResponse.getFields() != null) {
									if (singleEntryResponse.getFields().getImage() != null) {
										String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
												+ AsynConstants.assets + "/"
												+ singleEntryResponse.getFields().getImage().getSys().getId()
												+ "?access_token=" + AsynConstants.access_token;
										StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
										AssetImageResponse assetImageResponse = new Gson()
												.fromJson(imageResponse.toString(), AssetImageResponse.class);
										if (assetImageResponse != null) {
											if (assetImageResponse.getFields() != null) {
												if (assetImageResponse.getFields().getFile() != null) {
													if (assetImageResponse.getFields().getFile().getUrl() != null) {
														imgUrl = assetImageResponse.getFields().getFile().getUrl();
													}
												}
											}
										}
									}
									Testimonals testimonal = new Testimonals(singleEntryResponse.getFields().getTitle(),
											singleEntryResponse.getFields().getCompany(),
											singleEntryResponse.getFields().getContent(), imgUrl);
									testimonals.add(testimonal);
								}
							}

						}
					}
				}
			}
		}

		return ok(developers.render(solutionList, announcementList, testimonals, subTitle));
	}

	public static Result getContent(String slug) {
		DynamicPage dynamicPage = null;

		String contentUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/content_types?access_token="
				+ AsynConstants.access_token;
		StringBuffer loginResponse = BackendAPIServicesImpl.callGetAPI(contentUrl);
		ContentResponse apiResponse = new Gson().fromJson(loginResponse.toString(), ContentResponse.class);

		String url = "https://cdn.contentful.com/spaces/j6kwqi43xct3/entries?access_token=6be863b7ca36c67af0e01a22c82a3977a646bbc999b91b741ec82c93f3cc345a";
		StringBuffer headerResponse = BackendAPIServicesImpl.callGetAPI(url);

		beans.allentry.AllEntryResponse entryResponse = new Gson().fromJson(headerResponse.toString(),
				beans.allentry.AllEntryResponse.class);
		boolean isSlug = false;
		for (AllItem item : entryResponse.getItems()) {
			if (item.getFields() != null) {

				dynamicPage = new DynamicPage();

				if (item.getFields().getPageName() != null)
					dynamicPage.setPageName(item.getFields().getPageName());
			
				if (item.getFields().getPageContent() != null){

					if(item.getFields().getPageContent().contains("//")){


						Pattern p = Pattern.compile("(\\(.*\\))");   // the pattern to search for
						Matcher m = p.matcher(item.getFields().getPageContent()+"");
						String imageLogo ="";
						String replaceImageString="";
						if (m.find())
						{
							replaceImageString=item.getFields().getPageContent().trim().replace(m.group(0), " |").trim();
							imageLogo = m.group(1);
						}
						imageLogo=imageLogo.replaceAll("\\(", "");
						imageLogo=imageLogo.replaceAll("\\)", "");

						String[] pageContentArr=replaceImageString.split("\\|");
						
						dynamicPage.setImageLogo(imageLogo);
						dynamicPage.setPageContent(pageContentArr[0]);
						
						String boldStringArr[] = pageContentArr[1].split("\\r\\n|\\n|\\r");
					    
					    String pageContent2="";
					    
					    for(String addBoldStr:boldStringArr){
					    	  pageContent2=addBoldStr;
					    }
					    int index=replaceImageString.indexOf(pageContent2);
					    System.out.println("index--------"+index);
					    String newString = pageContentArr[1].substring(0, pageContentArr[1].length() - 10);
						dynamicPage.setPageContext2(newString);
					}else{
						dynamicPage.setPageContent(item.getFields().getPageContent());
					}
				}
					
				
				
				if (item.getFields().getSlug() != null){
					System.out.println("--------:  " + item.getFields().getSlug());

					if (item.getFields().getSlug().equals(slug)) {
						isSlug = true;
						dynamicPage.setSlug(item.getFields().getSlug());
						System.out.println("dynamicPage:" + dynamicPage.getSlug());

						System.out.println("slug:" + slug);
						return ok(dynamic_page.render(dynamicPage));
					}
				}
			}

		}
		if(!isSlug){
			 
					return ok(test.render());
				
		}
		return ok();
	}

	public static Result getSlugValue() {

		String url = "https://cdn.contentful.com/spaces/j6kwqi43xct3/entries?access_token=6be863b7ca36c67af0e01a22c82a3977a646bbc999b91b741ec82c93f3cc345a";
		StringBuffer headerResponse = BackendAPIServicesImpl.callGetAPI(url);

		beans.allentry.AllEntryResponse entryResponse = new Gson().fromJson(headerResponse.toString(),
				beans.allentry.AllEntryResponse.class);

		String slugValue = "";

		for (AllItem item : entryResponse.getItems()) {
			if (item.getFields() != null) {
				if (item.getFields().getSlug() != null)
					slugValue = item.getFields().getSlug();
			}
		}
		System.out.println("slugValue::::" + slugValue);
		return ok(Json.toJson(slugValue));

	}
	
	/*public static Result getResourceList() {
		String urlResourceList = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/" + AsynConstants.entries
				+ "?access_token=" + AsynConstants.access_token + "&content_type=" + session().get("resourceList");
		
		StringBuffer ResourceListResponse = BackendAPIServicesImpl.callGetAPI(urlResourceList);
		EntryResponse entryResponseResourceList = new Gson().fromJson(ResourceListResponse.toString(),
				EntryResponse.class);
		System.out.println("----------------entryResponseResourceList ::"+entryResponseResourceList);
		List<Resource> resources = new ArrayList<>();
		if(entryResponseResourceList!=null){
		
			for (beans.entry.Item item : entryResponseResourceList.getItems()) {

				if (item.getFields().getListName() != null) {
					if (item.getFields().getListName().equals("News")) {
						for (ListItem listItem : item.getFields().getListItems()) {
							String singleEntryUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
									+ AsynConstants.entries + "/" + listItem.getSys().getId() + "?access_token="
									+ AsynConstants.access_token;
							StringBuffer entrySingleResponse = BackendAPIServicesImpl.callGetAPI(singleEntryUrl);
							if (entrySingleResponse != null) {
								SingleEntryResponse singleEntryResponse = new Gson()
										.fromJson(entrySingleResponse.toString(), SingleEntryResponse.class);
								String imgUrl = null;
								if (singleEntryResponse != null) {
									if (singleEntryResponse.getFields() != null) {
										if (singleEntryResponse.getFields().getImage() != null) {
											String imgAPIUrl = AsynConstants.BASE_URL + "/" + AsynConstants.spaceId + "/"
													+ AsynConstants.assets + "/"
													+ singleEntryResponse.getFields().getImage().getSys().getId()
													+ "?access_token=" + AsynConstants.access_token;
											StringBuffer imageResponse = BackendAPIServicesImpl.callGetAPI(imgAPIUrl);
											AssetImageResponse assetImageResponse = new Gson()
													.fromJson(imageResponse.toString(), AssetImageResponse.class);
											if (assetImageResponse != null) {
												if (assetImageResponse.getFields() != null) {
													if (assetImageResponse.getFields().getFile() != null) {
														if (assetImageResponse.getFields().getFile().getUrl() != null) {
															imgUrl = assetImageResponse.getFields().getFile().getUrl();
														}
													}
												}
											}
											Testimonals testimonal = new Testimonals(
													singleEntryResponse.getFields().getTitle(),
													singleEntryResponse.getFields().getCompany(),
													singleEntryResponse.getFields().getContent(), imgUrl);
											testimonals.add(testimonal);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ok();
	}*/

}
