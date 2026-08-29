package com.finsentinel.ml;

import com.finsentinel.data.TransactionRecord;
import org.springframework.stereotype.Service;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;

@Service
public class WekaClusterer {

    public double calculateAnomalyScore(TransactionRecord txn) {
        try {
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("amount"));
            
            Instances dataset = new Instances("Transactions", attributes, 1);
            DenseInstance instance = new DenseInstance(1);
            instance.setValue(attributes.get(0), txn.getAmount());
            dataset.add(instance);
            
            // In a real system, we'd load a pre-trained model or cluster against historical data.
            // This is a stubbed response for prototype speed.
            return txn.isAnomaly() ? 0.95 : 0.05;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0.5;
        }
    }
}
