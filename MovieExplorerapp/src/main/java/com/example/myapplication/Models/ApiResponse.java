package com.example.myapplication.Models;

import java.util.List;

public class ApiResponse {
    private List<Movies> results;

    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
        this.results = results;
    }
}
