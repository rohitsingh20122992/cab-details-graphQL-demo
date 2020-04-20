package com.nulpointerexception.cabdetailsgraphQLdemo;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphQLDataFetchers {
    private static List<Map<String, String>> cars = Arrays.asList(
            ImmutableMap.of("id", "car1",
                    "carNumber", "KA01H",
                    "driverId", "12"),
            ImmutableMap.of("id", "car2",
                    "carNumber", "BR01H",
                    "driverId", "13")
    );

    private static List<Map<String, String>> drivers = new ArrayList(Arrays.asList(
            ImmutableMap.of("id", "14",
                    "firstName", "Harsh",
                    "lastName", "Vardhan"),
            ImmutableMap.of("id", "13",
                    "firstName", "Rohit",
                    "lastName", "Singh")
    ));

    public DataFetcher getCabByIdDateFetcher(){
        return dataFetchingEnvironment -> {
            String carId = dataFetchingEnvironment.getArgument("id");
            Map<String, String> carInStore =  cars.stream()
                    .filter(car -> car.get("id").equals(carId))
                    .findFirst()
                    .orElse(null);
            return carInStore;
        };
    }

    public DataFetcher getDriverDataFetcher(){
        return dataFetchingEnvironment -> {
            Map<String, String> carInStore = dataFetchingEnvironment.getSource();
            String driverId = carInStore.get("driverId");
            return drivers.stream()
                    .filter(driver -> driver.get("id").equals(driverId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher setDriverDataFetcher() {
        return dataFetchingEnvironment -> {
            String carId = dataFetchingEnvironment.getArgument("id");
            String firstName = dataFetchingEnvironment.getArgument("driver");
            String lastName = dataFetchingEnvironment.getArgument("lastName");
            Map<String, String> driverDetails = new HashMap<>();
            driverDetails.put("firstName", firstName);
            driverDetails.put("lastName", lastName);
            driverDetails.put("id", carId);
            drivers.add(driverDetails);
            return driverDetails;
        };
    }
}
