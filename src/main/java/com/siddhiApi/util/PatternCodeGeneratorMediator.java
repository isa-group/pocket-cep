package com.siddhiApi.util;

import com.siddhiApi.entity.Pattern;
import com.siddhiApi.exceptions.NotFoundException;
import com.siddhiApi.services.StreamService;
import com.siddhiApi.services.StreamServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatternCodeGeneratorMediator {

    static Logger logger = LoggerFactory.getLogger(PatternCodeGeneratorMediator.class);

    private static final StreamService streamService = new StreamServiceImpl();

    private static final StreamGenerator streamGenerator = new StreamGeneratorSiddhi();

    public static String getFullApplicationCode(Pattern pattern) throws NotFoundException {
        String applicationCode = "";
        if(pattern.getInputStreamNames().length == 0){
            throw new NotFoundException("At least one input stream must be defined.");
        }
        for(String inputStreamName: pattern.getInputStreamNames()){
            logger.info("InputStreamName: " + inputStreamName);
            //logger.info("InputStreamName from stream itself: " + streamService.getStream(inputStreamName).getStreamID());
            try {
                applicationCode += streamGenerator.generateCodeInputStream(streamService.getStream(inputStreamName));
            } catch (NotFoundException e) {
                throw new NotFoundException("There is no input stream with the ID passed.");
            }
        }
        applicationCode += pattern.getPatternCode();
        try {
            applicationCode += streamGenerator.generateCodeOutputStream(streamService.getStream(pattern.getOutputStreamName()));
        } catch (NotFoundException e) {
            throw new NotFoundException("There is no output stream with the ID passed.");
        }

        logger.info("Application code: " + applicationCode);
        return applicationCode;
    }
}
