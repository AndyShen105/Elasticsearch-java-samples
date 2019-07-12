//Created by AndyShen on 2019.7.12

import java.net.InetAddress;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

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
            ESSample.QueryDoc(client, indexName, typeName, "1");
            ESSample.UploadData(client, indexName, typeName, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
