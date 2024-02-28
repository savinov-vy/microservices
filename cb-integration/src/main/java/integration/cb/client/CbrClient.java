package integration.cb.client;

import integration.cb.config.CurrencyClientConfig;
import integration.cb.dto.generated.ValCurs;
import integration.cb.exceptions.ResourceNotFoundException;
import integration.cb.utils.DateFormatUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CbrClient extends WebServiceGatewaySupport {

    private final CurrencyClientConfig clientConfig;
    private final HttpClient client;

    public ValCurs getCursToday() {
        try {
            HttpResponse<String> response = client.send(request(), HttpResponse.BodyHandlers.ofString());
            return Optional.ofNullable(unmarshall(response.body())).orElseThrow();
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private HttpRequest request() {
        return HttpRequest.newBuilder(URI.create(uri()))
                .POST(HttpRequest.BodyPublishers.ofString(getHttpEntity()))
                .build();
    }

    private String uri() {
        String paramName = clientConfig.getParamName();
        String toDay = DateFormatUtils.toFormat(LocalDate.now(), clientConfig.getDatePattern());
        return UriComponentsBuilder.fromHttpUrl(clientConfig.getCbUrl())
                .queryParam(paramName, toDay)
                .build().toUriString();
    }

    private String getHttpEntity() {
        Resource resource = new ClassPathResource("xsd/ValCurs.xsd");
        try {
            return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private ValCurs unmarshall(String xml) {
        try (StringReader reader = new StringReader(xml)) {
            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            return (ValCurs) context.createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

}