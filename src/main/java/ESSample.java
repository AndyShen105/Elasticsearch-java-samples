//Created by AndyShen on 2019.7.12

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;

public class ESSample {

    public static void QueryDoc(TransportClient client, String index, String type, String id) throws Exception {

        GetResponse response = client.prepareGet(index, type, id).get();
        System.out.println(response.getSourceAsString());
    }

    public static void UploadData(TransportClient client, String index, String type, String json) throws Exception {

        IndexResponse response = client.prepareIndex( index, type)
                .setSource(json)
                .get();
        System.out.println(response.status());
    }
}

