package com.siddhiApi.util;

import com.siddhiApi.entity.Pattern;
import com.siddhiApi.exceptions.DuplicatedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CyclePatternChecker {
    static Logger logger = LoggerFactory.getLogger(CyclePatternChecker.class);

    public static boolean checkCyclePattern(Pattern newPattern, Pattern[] patternsAlreadyInSystem) throws DuplicatedEntity {
        ArrayList<Pattern> patternsList = new ArrayList<>(Arrays.asList(patternsAlreadyInSystem));
        if (patternsList.contains(newPattern))
            throw new DuplicatedEntity("This pattern already exists in the system");

        Stack<Pattern> patternStack = new Stack<>();
        patternsList.add(newPattern);
        patternStack.push(newPattern);
        do {
            Pattern patternBeingEvaluated = patternStack.pop();
            List<Pattern> listOfConnectedPatterns = patternsList.stream()
                    .filter(pattern1 -> Arrays.asList(pattern1.getInputStreamNames()).contains(patternBeingEvaluated.getOutputStreamName()))
                    .collect(Collectors.toList());

            if (listOfConnectedPatterns.size() > 0){
                if (listOfConnectedPatterns.contains(newPattern))
                    return false;

                for (Pattern connectedPattern: listOfConnectedPatterns){
                    patternStack.push(connectedPattern);
                }
            }
        }while(!patternStack.empty());

        return true;
    }
}
