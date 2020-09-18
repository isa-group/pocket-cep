package com.siddhiApi.inMemoryStorage;

import com.siddhiApi.entity.Pattern;
import com.siddhiApi.exceptions.DuplicatedEntity;
import com.siddhiApi.exceptions.NotFoundException;
import io.siddhi.query.api.expression.condition.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PatternsDatabase {
    private Set<Pattern> patterns;
    private static PatternsDatabase patternsDatabase;

    Logger logger = LoggerFactory.getLogger(PatternsDatabase.class);

    private PatternsDatabase() {
        this.patterns = new HashSet<>();
    }

    public static PatternsDatabase getPatternsDatabase(){
        if (patternsDatabase == null){
            patternsDatabase = new PatternsDatabase();
        }
        return patternsDatabase;
    }

    public Pattern[] getPatterns() {
        Pattern[] patternsArray = new Pattern[patterns.size()];
        int i = 0;
        for (Pattern pattern : patterns){
            patternsArray[i] = pattern;
            ++i;
        }
        return patternsArray;
    }

    public void setPatterns(Set<Pattern> patterns) {
        this.patterns = patterns;
    }

    public void addPattern(Pattern pattern) throws DuplicatedEntity {
        logger.info("Pattern to add: " + pattern.getPatternName());
        if (patterns.contains(pattern)){
            throw new DuplicatedEntity("There is an existent pattern with this id already.");
        }
        patterns.add(pattern);
    }

    public Pattern getPattern(String id) throws NotFoundException {
        for (Pattern pattern: patterns){
            if (pattern.getPatternName().equals(id)){
                return pattern;
            }
        }
        throw new NotFoundException("There is no pattern with that name.");
    }

    public void removePattern(String id) throws NotFoundException {
        Pattern patternToRemove = this.getPattern(id);
        if (!patterns.contains(patternToRemove)){
            throw new NotFoundException("The pattern could not be found, and therefore, could not be found.");
        }
        logger.info("Pattern to remove: " + patternToRemove.getPatternName());
        patterns.remove(patternToRemove);
        logger.info("Patterns size: " + patterns.size());
    }
}
