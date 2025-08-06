package com.kaiostavares.apiGateway.factory;

import com.kaiostavares.apiGateway.enums.ServiceType;

public interface ServicesConnectionParametersFactory {
    ConnectionParameters create(ServiceType serviceType);
}
