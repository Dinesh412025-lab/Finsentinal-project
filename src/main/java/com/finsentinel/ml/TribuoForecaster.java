package com.finsentinel.ml;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TribuoForecaster {

    public static class ForecastData {
        public String day;
        public double expected;
        public double projected;
        public double confidenceMin;
        public double confidenceMax;

        public ForecastData(String day, double expected, double projected) {
            this.day = day;
            this.expected = expected;
            this.projected = projected;
            this.confidenceMin = projected * 0.9;
            this.confidenceMax = projected * 1.1;
        }
    }

    public List<ForecastData> generateForecast(int delayDays, double volumeMultiplier) {
        // In a real scenario, this would train a Tribuo regression model on historical data.
        // For this prototype, we simulate the model's output directly based on the delay parameter.
        
        List<ForecastData> data = new ArrayList<>();
        double base = 100000 * volumeMultiplier;
        
        for (int i = 0; i <= 90; i += 10) {
            double expected = base + (i * 2000 * volumeMultiplier);
            double actual = expected;
            
            if (i >= 30 && i < 30 + delayDays) {
                actual -= 40000 * volumeMultiplier; // Simulated anomaly impact
            }
            
            data.add(new ForecastData("Day " + i, expected, actual));
        }
        
        return data;
    }
}
