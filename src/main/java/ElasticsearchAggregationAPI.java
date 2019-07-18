//created by andyshen on 7.17.2019

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBounds;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;

public class ElasticsearchAggregationAPI {
    //a sample aggregations
    public static void SampleAggregations(TransportClient client, String index, String type){

        SearchResponse aggreResponse = client.prepareSearch()
                .setIndices(index)
                .setTypes(type)
                .addAggregation(
                        AggregationBuilders.terms("by_color").field("color")
                                .subAggregation(AggregationBuilders.avg("avg_price").field("price"))
                )
                .execute().actionGet();
        //aggregation and avr
        System.out.print(aggreResponse);
    }

    //Stats Aggregations
    public static void AggregationStats(TransportClient client, String index, String type){

        SearchResponse aggreResponse = client.prepareSearch()
                .setIndices(index)
                .setTypes(type)
                .addAggregation(
                        AggregationBuilders
                                .stats("aggstats")
                                .field("price")
                ).execute().actionGet();
        Stats aggStats = aggreResponse.getAggregations().get("aggstats");

        double min = aggStats.getMin();
        double max = aggStats.getMax();
        double avg = aggStats.getAvg();
        double sum = aggStats.getSum();
        long count = aggStats.getCount();

        EsLogger.logger.info("min:{}, max:{}, avg:{}, sum:{}, count:{} ", min, max, avg, sum, count);
    }

    // a sample of geo bounds aggregation
    public  static void GeoBoundsAggregation(TransportClient client, String index, String type){

        SearchResponse aggreResponse = client.prepareSearch()
                .setIndices(index)
                .setTypes(type)
                .addAggregation(
                                AggregationBuilders
                                .geoBounds("agg")
                                .field("location")
                                .wrapLongitude(true)
                ).execute().actionGet();
        GeoBounds agg = aggreResponse.getAggregations().get("agg");
        GeoPoint bottomRight = agg.bottomRight();
        GeoPoint topLeft = agg.topLeft();

        EsLogger.logger.info("bottomRight {}, topLeft {}", bottomRight, topLeft);

    }

}
