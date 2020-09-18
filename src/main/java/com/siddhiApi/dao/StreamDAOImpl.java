package com.siddhiApi.dao;

import com.siddhiApi.entity.Stream;
import com.siddhiApi.exceptions.DuplicatedEntity;
import com.siddhiApi.exceptions.NotFoundException;
import com.siddhiApi.inMemoryStorage.StreamDatabase;

import org.springframework.stereotype.Service;

@Service
public class StreamDAOImpl implements StreamDAO{

    private final StreamDatabase streamDatabase = StreamDatabase.getStreamDatabase();

    @Override
    public void createStream(Stream stream) throws DuplicatedEntity {
        streamDatabase.addStream(stream);
    }

    @Override
    public Stream[] getStreams() {
        return streamDatabase.getStreams();
    }

    @Override
    public Stream getStream(String stream) throws NotFoundException {
        return streamDatabase.getStream(stream);
    }

    @Override
    public void removeStream(String stream) throws NotFoundException {
        streamDatabase.removeStream(stream);
    }
}
