package com.malynovsky.task.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "custom")
public class OtpLimits {
    private int leftLimit;
    private int rightLimit;
    private int maxSize;
}
