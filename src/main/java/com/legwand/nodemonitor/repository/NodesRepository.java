package com.legwand.nodemonitor.repository;

import com.legwand.nodemonitor.model.Node;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class NodesRepository {

    private final static Logger LOG = Logger.getLogger(NodesRepository.class.getName());

    @Autowired
    private MongoOperations mongoOperations;

    public Node saveNode(Node node) {
        return mongoOperations.save(node);
    }

    public Node updateNode(Node node) {
        return mongoOperations.save(node);
    }

    public Node setTaskEnabled(String nodeId, boolean isEnabled) {
        Node node = mongoOperations.findOne(query(where("_id").is(nodeId)), Node.class);
        if (node != null) {
            node.setNodeCheckActive(isEnabled);
            return mongoOperations.save(node);
        } else {
            return null;
        }
    }

    public boolean nodeExists(String nodeId) {
        return mongoOperations.exists(query(where("_id").is(nodeId)), Node.class);
    }

    public Node findNode(String nodeId) {
        return mongoOperations.findOne(query(where("_id").is(nodeId)), Node.class);
    }

    public List<Node> getAllNodes() {
        return mongoOperations.findAll(Node.class);
    }

    public void remoteNode(String nodeId) {
        DeleteResult deleteResult = mongoOperations.remove(query(where("_id").is(nodeId)), Node.class);
        LOG.info(MessageFormat.format("Node removed: {0}", deleteResult));
    }


}
