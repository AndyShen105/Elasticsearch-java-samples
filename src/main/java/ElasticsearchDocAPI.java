//Created by AndyShen on 2019.7.12

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ElasticsearchDocAPI {

    public static void CreateIndexMapping(TransportClient client, String index, String type) throws Exception{

        //creat an index
        CreateIndexRequestBuilder cib=client.admin().indices().prepareCreate(index);

        //create an mapping
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("account_number")
                .field("type","text")
                .endObject()
                .startObject("title")
                .field("type","text")
                .endObject()
                .startObject("balance")
                .field("type","text")
                .endObject()
                .startObject("firstname")
                .field("type","text")
                .endObject()
                .startObject("lastname")
                .field("type","text")
                .endObject()
                .startObject("age")
                .field("type","short")
                .endObject()
                .startObject("adress")
                .field("type","text")
                .endObject()
                .startObject("employer")
                .field("type","text")
                .endObject()
                .startObject("email")
                .field("type","text")
                .endObject()
                .startObject("city")
                .field("type","text")
                .endObject()
                .startObject("state")
                .field("type","text")
                .endObject()
                .endObject()
                .endObject();

        cib.addMapping(type, mapping);
        CreateIndexResponse res=cib.execute().actionGet();

        EsLogger.logger.info(" Creat index successful  ");

    }

    //obtain a doc from index
    public static void QueryDoc(TransportClient client, String index, String type, String id) throws Exception {

        GetResponse response = client.prepareGet(index, type, id).get();
        System.out.println(response.getSourceAsString());
    }

    //del a doc from index
    public static void DelDoc(TransportClient client, String index, String type, String id) throws Exception {

        DeleteResponse response = client.prepareDelete(index, type, id).get();
        System.out.println(response.status());
    }

    //del an index
    public static void DelIndex(TransportClient client, String index) throws Exception {

        DeleteIndexResponse response = client.admin().indices().prepareDelete(index).execute().actionGet();
        System.out.println(response.toString());
    }

    //insert a doc into index
    public static void UploadData(TransportClient client, String index, String type, String json) throws Exception {

        IndexResponse response = client.prepareIndex( index, type)
                .setSource(json, XContentType.JSON)
                .get();
        System.out.println(response.status());
    }

    //upload a file into index
    public static void UploadFile(TransportClient client, String index, String type, String filepath) throws Exception {

        BufferedReader reader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);

            BulkRequestBuilder bulkRequest=client.prepareBulk();
            String sourceString = null;

            int count = 0;
            while ((sourceString = reader.readLine()) != null ) {
                if(count%2==1){
                    String id = String.valueOf(count/2);
                    bulkRequest.add(client.prepareIndex(index, type).setSource(sourceString, XContentType.JSON).setId(id));
                    if (count%10==0) {
                        bulkRequest.execute().actionGet();
                    }
                }
                count++;
            }

            bulkRequest.execute().actionGet();

            EsLogger.logger.info(" Upload a file successful  ");
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

    //upload a file into index with api"bulkprocesser"
    public static void UploadFileWithBulkPross(TransportClient client, String index, String type, String filepath, int nitem) throws Exception {
        String[] data = ReadJson.ReadLoadFile(filepath, nitem);
        BulkProcessor.Builder builder = BulkProcessor.builder(client,
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId,
                                           BulkRequest request) {

                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        System.out.println("error：" + failure.getMessage() + " \ncause：" + failure.getCause());
                    }
                });
        builder.setBulkActions(10);
        builder.setBulkSize(new ByteSizeValue(20, ByteSizeUnit.MB));
        builder.setFlushInterval(TimeValue.timeValueSeconds(1));
        builder.setConcurrentRequests(1);
        BulkProcessor bulkProcessor = builder
                .build();
        for(int id=0; id<nitem; id++)
            bulkProcessor.add(new IndexRequest(index, type, String.valueOf(id)).source(data[id], XContentType.JSON));
        bulkProcessor.close();
    }


}
