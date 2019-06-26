import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by liutq on 2019/2/19.
 */
public class TestYaml {
    public static void main(String[] args) throws IOException, URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        byte[] token = Base64Utils.encode(("config:cf123456").getBytes());
        headers.add(HttpHeaders.AUTHORIZATION,"Basic " + new String(token));
        RequestEntity requestEntity = new RequestEntity(headers,HttpMethod.GET,new URI("http://192.168.198.128:7000/master/oauth-test.yml"));
        ResponseEntity<String> res = new RestTemplate().exchange(requestEntity,String.class);
        String s = res.getBody();
        System.err.println(s);

//        DumperOptions dumperOptions = new DumperOptions();
//        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
//        dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
//        dumperOptions.setPrettyFlow(false);
//        Yaml y = new Yaml(dumperOptions);
//        System.err.println(y.dump(properties));
    }
}
