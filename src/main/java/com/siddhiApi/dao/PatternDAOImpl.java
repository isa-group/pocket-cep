package com.siddhiApi.dao;


import com.siddhiApi.exceptions.DuplicatedEntity;
import com.siddhiApi.exceptions.NotFoundException;
import com.siddhiApi.exceptions.SiddhiAppException;
import com.siddhiApi.inMemoryStorage.PatternsDatabase;
import com.siddhiApi.multithreading.EventExecutor;
import com.siddhiApi.multithreading.SendEventProducerConsumer;
import com.siddhiApi.siddhiApplicationManager.SiddhiApplicationManager;
import com.siddhiApi.entity.Pattern;
import io.siddhi.core.exception.SiddhiAppCreationException;
import io.siddhi.query.api.exception.SiddhiAppValidationException;
import io.siddhi.query.compiler.exception.SiddhiParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatternDAOImpl implements PatternDAO {

	Logger logger = LoggerFactory.getLogger(PatternDAOImpl.class);

	private PatternsDatabase patternsDatabase = PatternsDatabase.getPatternsDatabase();

	public void runPattern(Pattern pattern) throws DuplicatedEntity, SiddhiAppException {
		// TODO Auto-generated method stub
		logger.info("Pattern Name: " + pattern.getPatternName());
		for (String inputStream: pattern.getInputStreamNames()){
			logger.info("Pattern Input Stream Name: " + inputStream);
		}
		logger.info("Pattern Output Stream: " + pattern.getOutputStreamName());
		logger.info("Pattern Code: " + pattern.getPatternCode());
		patternsDatabase.addPattern(pattern);
		try{
			SiddhiApplicationManager.runApp(
					pattern.getPatternName(),
					pattern.getInputStreamNames(),
					pattern.getOutputStreamName(),
					pattern.getPatternCode()
			);
		}catch(SiddhiAppException e){
			try {
				logger.info("Removing pattern.");
				patternsDatabase.removePattern(pattern.getPatternName());
			} catch (NotFoundException notFoundException) {
				notFoundException.printStackTrace();
			}
			throw new SiddhiAppException(e);
		}
	}

	@Override
	public Pattern[] getPatterns() {
		return patternsDatabase.getPatterns();
	}

	@Override
	public Pattern getPattern(String patternName) throws NotFoundException {
		return patternsDatabase.getPattern(patternName);
	}

	@Override
	public List<String> getPatternsRunning() {
		return SiddhiApplicationManager.applications();
	}

	@Override
	public void removePattern(String patternName) throws NotFoundException {
		patternsDatabase.removePattern(patternName);
		SiddhiApplicationManager.stopApp(patternName);
	}


	/*public void stopPattern(String patternName) {
		// TODO Auto-generated method stub
		SiddhiApplicationManager.stopApp(patternName);
	}*/


	public void sendEvent(String streamName, Object[] event) {
		// TODO Auto-generated method stub
		//SiddhiApplicationManager.sendEvent(streamName, event);
		SendEventProducerConsumer.addPetition(new SendEventProducerConsumer.SendEventPetition(streamName, event));
	}
}
