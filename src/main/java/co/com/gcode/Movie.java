package co.com.gcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Movie {
    private String name;
    private int durationInMin;
    private double score;
    private String director;
}
