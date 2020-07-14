package app;

import lombok.Builder;
import lombok.Data;
// class which holds the data fetched from the network
@Data
@Builder
public class ResponseModel {
    private final String ip;
    private final String hostname;
    private final String city;
    private final String region;
    private final String country;
    private final String timeZone;
    private final String loc;
}
