package com.siddhiApi.inMemoryStorage;

import java.util.HashSet;
import java.util.Set;

public class IdGenerator {
    private Set<String> idsSubscriptionSet;
    private static IdGenerator idGenerator;


    private IdGenerator() {
        this.idsSubscriptionSet = new HashSet<>();
    }

    public static IdGenerator getIdGenerator() {
        if(idGenerator == null){
            idGenerator = new IdGenerator();
        }
        return idGenerator;
    }

    public Set<String> getIdsSubscriptionSet() {
        return idsSubscriptionSet;
    }

    public void setIdsSubscriptionSet(Set<String> idsSubscriptionSet) {
        this.idsSubscriptionSet = idsSubscriptionSet;
    }

    public String getUniqueId(){
        String ID;
        do {
            ID = "AD";
        }while(idsSubscriptionSet.contains(ID));
        return ID;
    }
}
