package uk.co.sainsburys.clients;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import uk.co.sainsburys.parameters.Parameter;

@Service
public class ParametersService {
    private Map<String, Parameter> cache = null;

    private final ParametersClient parametersClient;

    public ParametersService(ParametersClient parametersClient) {
        this.parametersClient = parametersClient;
    }

    public void updateParameter(Parameter parameter) {
        if(cache == null) {
           cacheParameters();
        }

        cache.put(parameter.getName(), parameter);
    }

    public Parameter readParameter(String parameterName) {
        if(cache == null) {
            cacheParameters();
        }

        return cache.getOrDefault(parameterName, null);
    }

    private void cacheParameters() {
        cache = new ConcurrentHashMap<>();

        List<Parameter> parameters = parametersClient.retrieveParameters();

        parameters.forEach(param -> cache.put(param.getName(), param));
    }

    @FeignClient(name = "sainsburys-PARAMETERS")
    public interface ParametersClient {
        @GetMapping("/sainsburys/parameters/")
        List<Parameter> retrieveParameters();

    }
}
