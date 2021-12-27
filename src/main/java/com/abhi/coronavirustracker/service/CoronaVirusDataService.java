package com.abhi.coronavirustracker.service;

import com.abhi.coronavirustracker.models.locationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {


    private static String virusDataUrl="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
private List<locationStats> allStats=new ArrayList<>();

    @PostConstruct//when sppring will create a class of this this method will execute
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<locationStats> newStats=new ArrayList<>();
        HttpClient client=HttpClient.newHttpClient();
    HttpRequest request=HttpRequest.newBuilder()
            .uri(URI.create(virusDataUrl)).
            build();

HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader in = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);


        for (CSVRecord record : records) {

            locationStats locationStats=new locationStats();
           locationStats.setState(record.get("Province/State"));
           locationStats.setCountry(record.get("Country/Region"));
           locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
         newStats.add(locationStats);
        }
        this.allStats=newStats;

    }


}
