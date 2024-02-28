package integration.cb.service;

import integration.cb.client.CbrClient;
import integration.cb.dto.generated.ValCurs;
import integration.cb.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record CbCursService(CbrClient client) {

    public String getCursTodayByCode(String code) {
        return getAllCursOnToday().stream()
                .filter(valute -> code.equals(valute.getCharCode()))
                .map(ValCurs.Valute::getValue)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("A currency with name code: '%s' is not found", code));
    }

    private List<ValCurs.Valute> getAllCursOnToday() {
        return client.getCursToday().getValute();
    }

}