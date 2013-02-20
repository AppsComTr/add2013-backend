package org.gdgankara.app.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@XmlRootElement
public class Version {
	public static final String KIND = "version";
	public static final String NUMBER = "number";

	private long id;
	private long number;

	public Version() {

	}

	public Version(long id, long number) {
		super();
		this.id = id;
		this.number = number;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public static Version getVersion() {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity eVersion;
		Query versionQuery = new Query(KIND).addSort(NUMBER, SortDirection.DESCENDING);
		PreparedQuery preparedQuery = dataStore.prepare(versionQuery);
		List<Entity> entityList = preparedQuery.asList(FetchOptions.Builder
				.withLimit(1));
		
		if (entityList.size() > 0) {
			eVersion = entityList.get(0);
			return new Version(eVersion.getKey().getId(), (Long)eVersion.getProperty(NUMBER));
		} else {
			return new Version(0,0);
		}
		
	}

	public static Version setVersion() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		Long number = Long.valueOf(dateFormat.format(cal.getTime()));
		
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity eVersion;
		Query spekaerQuery = new Query(KIND).addSort(NUMBER, SortDirection.DESCENDING);
		PreparedQuery preparedQuery = dataStore.prepare(spekaerQuery);
		List<Entity> entityList = preparedQuery.asList(FetchOptions.Builder
				.withLimit(1));
		
		if (entityList.size() == 0) {
			eVersion = new Entity(KIND);
			eVersion.setProperty(NUMBER, number);
			dataStore.put(eVersion);
		} else {
			eVersion = entityList.get(0);
			eVersion.setProperty(NUMBER, number);
			dataStore.put(eVersion);
		}
		return new Version(eVersion.getKey().getId(), (Long)eVersion.getProperty(NUMBER));
	}

}
