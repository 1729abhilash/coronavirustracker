package com.abhi.coronavirustracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class locationStats {
    private String state;
    private String country;
    private int latestTotalCases;


}
