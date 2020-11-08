package am.itspace.projectscope.util;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RandomStringGeneratorUtil {
    public static String uuId() {
        return generateRandomStringByUUID();
    }
    private static String generateRandomStringByUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}