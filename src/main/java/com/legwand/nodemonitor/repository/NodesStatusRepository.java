package com.legwand.nodemonitor.repository;

import com.legwand.nodemonitor.model.NodeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class NodesStatusRepository {

    @Autowired
    private MongoOperations mongoOperations;

    public NodeStatus saveNodeStatusResult(NodeStatus nodeStatus) {
        return mongoOperations.save(nodeStatus);
    }

    public List<NodeStatus> getNodeStatusList(String nodeId) {
        return mongoOperations.find(query(where("nodeId").is(nodeId)), NodeStatus.class);
    }

    public NodeStatus getLatestNodeStatus(String nodeId) {
        Query query = query(where("nodeId").is(nodeId)).with(Sort.by(Sort.Direction.DESC, "date")).limit(1);
        return mongoOperations.find(query, NodeStatus.class).get(0);
    }

    
}
