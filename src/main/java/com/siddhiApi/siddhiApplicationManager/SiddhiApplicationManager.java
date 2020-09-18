package com.siddhiApi.siddhiApplicationManager;

import com.siddhiApi.exceptions.DuplicatedEntity;
import com.siddhiApi.exceptions.SiddhiAppException;
import io.siddhi.core.exception.SiddhiAppCreationException;
import io.siddhi.query.api.exception.SiddhiAppValidationException;
import io.siddhi.query.compiler.exception.SiddhiParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiddhiApplicationManager {
	static Logger logger = LoggerFactory.getLogger(SiddhiApplicationManager.class);
	private static final Map<String, RunSiddhiApplication> applications = new HashMap<String, RunSiddhiApplication>();
	private static final Map<String, List<String>> inputStreamInApplication = new HashMap<>();

	public static synchronized void runApp(String applicationName, String[] inputStreamNames, String outputStreamName, String streamImplementation) throws DuplicatedEntity, SiddhiAppException {
		//String fileName = file.substring(file.lastIndexOf("\\") + 1, file.indexOf("."));
		logger.info("applicationName: " + applicationName);
		if(applications.containsKey(applicationName)){
			logger.info("applicationName already appears: " + applicationName);
			throw new DuplicatedEntity("A pattern with that name already exists.");
		}
		try{
			applications.put(applicationName, new RunSiddhiApplication());
			applications.get(applicationName).runApp(inputStreamNames, outputStreamName, streamImplementation);
		}catch(SiddhiAppCreationException | SiddhiParserException | SiddhiAppValidationException e){
			applications.remove(applicationName);
			throw new SiddhiAppException(e);
		}
		for(String inputStreamName: inputStreamNames){
			addApplicationToInputStream(inputStreamName, applicationName);
		}
	}

	//TODO Refactor this function, so it does not take n power n time. And it can do it in n.
	public static synchronized void stopApp(String app) {
		if (applications.containsKey(app)){
			applications.get(app).stopApp();
			applications.remove(app);
			for (String key : inputStreamInApplication.keySet()) {
				if (inputStreamInApplication.get(key).contains(app)) {
					removeApplicationFromInputStream(key, app);
				}
			}
		}
	}
	
	public static synchronized List<String> applications() {
		List<String> appsNames = new ArrayList<String>();
		for(String name: applications.keySet())
			appsNames.add(name);
		
		return appsNames;
	}
	
	public static synchronized void sendEvent(String streamName, Object[] event) {
		List<String> applicationsListContainsStream = inputStreamInApplication.get(streamName);
		if(applicationsListContainsStream != null){
			for (String application:applicationsListContainsStream){
				applications.get(application).sendEvent(streamName, event);
			}
		}
	}

	private static synchronized void addApplicationToInputStream(String inputStreamName, String applicationName){
		if (!inputStreamInApplication.containsKey(inputStreamName)){
			inputStreamInApplication.put(inputStreamName, new ArrayList<>());
		}
		inputStreamInApplication.get(inputStreamName).add(applicationName);
	}

	private static synchronized void removeApplicationFromInputStream(String inputStreamName, String applicationName){
		inputStreamInApplication.get(inputStreamName).remove(applicationName);
		if (inputStreamInApplication.get(inputStreamName).size() == 0){
			inputStreamInApplication.remove(inputStreamName);
		}
	}
}