package uk.co.sainsburys.parameters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import uk.co.sainsburys.parameters.notifier.ParameterChangeNotifier;
import uk.co.sainsburys.parameters.store.ParameterStore;

@Service
public class ParametersService {
    private final ParameterStore parameterStore;
    private final ParameterChangeNotifier parameterChangeNotifier;

    public ParametersService(ParameterStore parameterStore, ParameterChangeNotifier parameterChangeNotifier) {
        this.parameterStore = parameterStore;
        this.parameterChangeNotifier = parameterChangeNotifier;
    }

    public List<Parameter> retrieveParameters() {
        return parameterStore.retrieveAll();
    }

    public void addParameter(Parameter parameter) {
        parameterChangeNotifier.notify(parameter, ParameterChangeNotifier.NotificationType.ADDED);
        parameterStore.save(parameter);
    }

    public Optional<Parameter> retrieveParameter(String parameterName) {
        return parameterStore.retrieveByName(parameterName);
    }
}
