//Created by AndyShen on 2019.7.12

import java.net.InetAddress;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.fasterxml.jackson.databind.*;

public class HelloES {

    public static void main(String[] args){

        String clusterName = "elasticsearch";
        String clusterHost = "localhost";
        String indexName = "bank";
        String typeName = "account";
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        int clustPort = 9300;

        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(clusterHost), clustPort));

            //test-1: create an index
            ESSample.CreateIndexMapping(client, indexName, typeName);

            //test-2: upload a file into index
            ESSample.UploadFile(client, indexName, typeName, "/home/andyshen/HelloES/data/accounts.json");

            //test-3: upload a data
            ESSample.UploadData(client, indexName, typeName, json);

            //test-4: obtain a doc
            ESSample.QueryDoc(client, indexName, typeName, "1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
