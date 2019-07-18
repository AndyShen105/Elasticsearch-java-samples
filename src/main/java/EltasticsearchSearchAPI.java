import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.HashMap;
import java.util.Map;

public class EltasticsearchSearchAPI {

    /**search a doc with id
     *本质是from+size,因此其最大值已经固定为10000， 超过则报错
     * */
    public static SearchHit[] SearchDoc(TransportClient client, String index, String type, int size){

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch();
        searchRequestBuilder.setIndices(index);
        searchRequestBuilder.setTypes(type);
        searchRequestBuilder.setSize(size);

        SearchResponse searchResponse = searchRequestBuilder.get();

        SearchHit[] searchHits = searchResponse.getHits().getHits();
        return searchHits;

    }

    //search with scrolls
    public static void SearchWithScrolls(TransportClient client, String index, String type){

        SearchResponse searchResponse = client.prepareSearch(index)
                .setIndices(index)
                .setTypes(type)
                .setScroll(new TimeValue(60000))
                .setSize(100).get();

        int count = 0;
        do {
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            count += searchHits.length;
            System.out.print("Get "+String.valueOf(count)+" docs.\r\n");
            //获取scrollid 同时重新设定滚动参数
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while(searchResponse.getHits().getHits().length != 0);

    }

    //search temolate
    public static void SearchWithTemplate(TransportClient client){

        Map<String, Object> template_params = new HashMap<>();
        template_params.put("param_gender", "Nanette");
        SearchResponse searchResponse = new SearchTemplateRequestBuilder(client)
                .setScript("template_gender")
                .setScriptType(ScriptType.FILE)
                .setScriptParams(template_params)
                .setRequest(new SearchRequest())
                .get()
                .getResponse();

        SearchHit[] searchHits = searchResponse.getHits().getHits();
        //System.out.print("Get "+String.valueOf(searchHits.length)+" docs.\r\n");
    }
}
