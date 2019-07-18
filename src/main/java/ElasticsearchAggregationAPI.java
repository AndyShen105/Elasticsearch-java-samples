//created by andyshen on 7.17.2019

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBounds;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;

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

    //a sample of filter aggregation
    public static void FilterAggregation(TransportClient client, String index, String type){

        SearchResponse searchResponse = client.prepareSearch()
                .setIndices(index)
                .setTypes(type)
                .addAggregation(
                        AggregationBuilders
                        .filter("agg", QueryBuilders.termQuery("age", 31))
                ).execute().actionGet();
        Filter agg = searchResponse.getAggregations().get("agg");

        EsLogger.logger.info("Count of results: {}", agg.getDocCount());
    }

    //a sample of Geo Distance aggregation
    public static void GeoDisAggregation(TransportClient client, String index, String type){

        //"arc": more accurate "plane": more fast
        AggregationBuilder aggregation =
                AggregationBuilders
                        .geoDistance("agg", new GeoPoint(48.84237171118314,2.33320027692004))
                        .field("address.location")
                        .unit(DistanceUnit.KILOMETERS)
                        .distanceType(GeoDistance.PLANE)
                        .addUnboundedTo(3.0)
                        .addRange(3.0, 10.0)
                        .addRange(10.0, 500.0);

        SearchResponse searchResponse = client.prepareSearch()
                .setIndices(index)
                .setTypes(type)
                .addAggregation(aggregation).execute().actionGet();


        Range agg = searchResponse.getAggregations().get("agg");

        // For each entry
        for (Range.Bucket entry : agg.getBuckets()) {
            String key = entry.getKeyAsString();    // key as String
            Number from = (Number) entry.getFrom(); // bucket from value
            Number to = (Number) entry.getTo();     // bucket to value
            long docCount = entry.getDocCount();    // Doc count

            EsLogger.logger.info("key [{}], from [{}], to [{}], doc_count [{}]", key, from, to, docCount);
        }
    }

}
