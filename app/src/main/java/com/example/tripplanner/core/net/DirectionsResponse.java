package com.example.tripplanner.core.net;

import java.util.List;


public class DirectionsResponse {
    List<Route> routes;
    String status;

   public DirectionsResponse(List<Route> routes, String status) {
        this.routes = routes;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
