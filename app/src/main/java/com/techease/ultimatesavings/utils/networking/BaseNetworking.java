package com.techease.ultimatesavings.utils.networking;

public class BaseNetworking {
    public static APIServices services;

    public static APIServices apiServices() {
        services = APIClient.getClient().create(APIServices.class);
        return services;
    }
    public static APIServices apiServices(String token) {
        services = APIClient.getClient(token).create(APIServices.class);
        return services;
    }
}
