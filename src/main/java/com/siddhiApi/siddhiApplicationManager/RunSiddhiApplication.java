package com.siddhiApi.siddhiApplicationManager;

import com.siddhiApi.webhook.Webhook;
import com.siddhiApi.webhook.WebhookMediator;
import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.exception.SiddhiAppCreationException;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.stream.output.StreamCallback;
import io.siddhi.core.util.EventPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RunSiddhiApplication {
	static Logger logger = LoggerFactory.getLogger(RunSiddhiApplication.class);

	private static SiddhiManager siddhiManager = new SiddhiManager();
	private SiddhiAppRuntime siddhiAppRuntime;
	private Map<String, InputHandler> inputHandlerMap = new HashMap<String, InputHandler>();

	public void runApp(String[] inputStreamNames, String outputStreamName, String streamImplementation) throws SiddhiAppCreationException {
		//Siddhi Application
		String siddhiApp = streamImplementation;

		siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

		//Start event processing
		siddhiAppRuntime.start();

		siddhiAppRuntime.addCallback(outputStreamName, new StreamCallback() {
			@Override
			public void receive(io.siddhi.core.event.Event[] events) {
				EventPrinter.print(events);
				//new Webhook(events)
				for (io.siddhi.core.event.Event event: events){
					//logger.info("Event normal: " + event);
					WebhookMediator.webhookFromSiddhiApp(outputStreamName, event.getData());
				}

			}
		});

		for(String inputStreamName: inputStreamNames){
			inputHandlerMap.put(inputStreamName, siddhiAppRuntime.getInputHandler(inputStreamName));
		}
		logger.info("App running");
	}

	public void stopApp() {
		siddhiAppRuntime.shutdown();
	}

	public void sendEvent(String stream, Object[] event) {
		for(Object object: event){
			logger.info("Object class: " + object.getClass().toString());
		}
		try {
			inputHandlerMap.get(stream).send(event);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
