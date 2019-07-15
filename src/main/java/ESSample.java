//Created by AndyShen on 2019.7.12

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ESSample {

    public static void CreateIndexMapping(TransportClient client, String index, String type) throws Exception{

        CreateIndexRequestBuilder cib=client.admin().indices().prepareCreate(index);
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("account_number")
                .field("type","string")
                .endObject()
                .startObject("title")
                .field("type","string")
                .endObject()
                .startObject("balance")
                .field("type","string")
                .endObject()
                .startObject("firstname")
                .field("type","string")
                .endObject()
                .startObject("lastname")
                .field("type","string")
                .endObject()
                .startObject("age")
                .field("type","long")
                .endObject()
                .startObject("adress")
                .field("type","string")
                .endObject()
                .startObject("aemployer")
                .field("type","string")
                .endObject()
                .startObject("email")
                .field("type","string")
                .endObject()
                .startObject("city")
                .field("type","string")
                .endObject()
                .startObject("state")
                .field("type","string")
                .endObject()
                .endObject()
                .endObject();

        cib.addMapping(type, mapping);
        CreateIndexResponse res=cib.execute().actionGet();
        System.out.println("----------Creat index successful----------");
    }

    public static void QueryDoc(TransportClient client, String index, String type, String id) throws Exception {

        GetResponse response = client.prepareGet(index, type, id).get();
        System.out.println(response.getSourceAsString());
    }

    public static void UploadData(TransportClient client, String index, String type, String json) throws Exception {

        IndexResponse response = client.prepareIndex( index, type)
                .setSource(json, XContentType.JSON)
                .get();
        System.out.println(response.status());
    }

    public static void UploadFile(TransportClient client, String index, String type, String filepath) throws Exception {

        BufferedReader reader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            BulkRequestBuilder bulkRequest=client.prepareBulk();
            String tempString = null;
            int count = 0;
            while ((tempString = reader.readLine()) != null ) {
                if(count%2==1){
                    bulkRequest.add(client.prepareIndex(index, type).setSource(tempString, XContentType.JSON));
                    if (count%10==0) {
                        bulkRequest.execute().actionGet();
                    }
                }
                count++;
            }

            bulkRequest.execute().actionGet();
            System.out.println("----upload a file successful!-------");

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

