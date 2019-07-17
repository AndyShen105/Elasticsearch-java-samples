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
        String json = "{\"account_number\":1003,\"balance\":323321,\"firstname\":\"Am2ber\",\"lastname\":\"Duke\",\"age\":32,\"gender\":\"M\",\"address\":\"880 Holmes Lane\",\"employer\":\"Pyrami\",\"email\":\"amberduke@pyrami.com\",\"city\":\"Brogan\",\"state\":\"IL\"}";
        String filepath = "/home/andyshen/HelloES/data/accounts1.json";
        int clustPort = 9300;



        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(clusterHost), clustPort));

             /*
            // Samples of Document API
            //test-1: del an index
            ESSample.DelIndex(client, indexName);

            //test-2: create an index
            ESSample.CreateIndexMapping(client, indexName, typeName);

            //test-3: upload a file into index
            ESSample.UploadFile(client, indexName, typeName, filepath);

            //test-4: upload a data
            ESSample.UploadData(client, indexName, typeName, json);

            //test-5: obtain a doc
            ESSample.QueryDoc(client, indexName, typeName, "1");

            //test-6: del a doc
            ESSample.DelDoc(client, indexName, typeName, "10");

            //test-7: upload a file with bulkprocesser into index
            ESSample.UploadFileWithBulkPross(client, indexName, typeName, filepath, 1000);



            // Samples of search API
            //test-8: a sample search
            ESSample.SearchDoc(client, indexName, typeName, 2);

            //test-9: search with scroll
            ESSample.SearchWithScrolls(client, indexName, typeName);

            //test-10: search with template
            ESSample.SearchWithTemplate(client);
            */
            //test-11: aggregation

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
