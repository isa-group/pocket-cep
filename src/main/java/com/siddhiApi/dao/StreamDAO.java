package com.siddhiApi.dao;

import com.siddhiApi.entity.Stream;
import com.siddhiApi.exceptions.DuplicatedEntity;
import com.siddhiApi.exceptions.NotFoundException;

public interface StreamDAO {
    void createStream(Stream stream) throws DuplicatedEntity;

    Stream[] getStreams();

    Stream getStream(String stream) throws NotFoundException;

    void removeStream(String stream) throws NotFoundException;


}
