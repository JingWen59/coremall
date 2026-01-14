package com.atguigu.gulimall.diagnostic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

@Component
public class ConfigDiagnostics implements CommandLineRunner {

    @Autowired
    private ConfigurableEnvironment environment;

    @Override
    public void run(String... args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("配置诊断报告");
        System.out.println("=".repeat(60));

        System.out.println("\n=== 1. 所有配置源（按优先级从高到低）===");
        environment.getPropertySources().forEach(source -> {
            System.out.println("- " + source.getName());

            // 检查是否是Nacos配置源
            if (source.getName().contains("nacos") || source.getName().contains("NACOS") ||
                    source.getName().contains("bootstrapProperties")) {
                System.out.println("  ⭐ 疑似Nacos配置源");

                if (source instanceof MapPropertySource) {
                    MapPropertySource mapSource = (MapPropertySource) source;
                    System.out.println("    包含 " + mapSource.getSource().size() + " 个配置项");

                    if (mapSource.getSource().size() == 0) {
                        System.err.println("    ❌ 警告：配置源为空！");
                    } else {
                        // 打印前10个配置项
                        mapSource.getSource().entrySet().stream()
                                .limit(10)
                                .forEach(entry ->
                                        System.out.println("    " + entry.getKey() + " = " + entry.getValue())
                                );
                    }
                }
            }
        });

        System.out.println("\n=== 2. 特定配置项检查 ===");
        String[] keys = {"coupon.user.name", "coupon.user.age", "spring.application.name"};
        for (String key : keys) {
            String value = environment.getProperty(key, "未找到");
            System.out.println(key + " = " + value);

            // 找出这个配置的来源
            for (org.springframework.core.env.PropertySource<?> source : environment.getPropertySources()) {
                if (source.containsProperty(key)) {
                    System.out.println("  来源: " + source.getName());
                    break;
                }
            }
        }

        System.out.println("\n=== 3. Nacos相关属性检查 ===");
        System.out.println("spring.cloud.nacos.config.server-addr = " +
                environment.getProperty("spring.cloud.nacos.config.server-addr", "未设置"));
        System.out.println("spring.cloud.nacos.config.file-extension = " +
                environment.getProperty("spring.cloud.nacos.config.file-extension", "未设置"));
        System.out.println("spring.cloud.nacos.config.enabled = " +
                environment.getProperty("spring.cloud.nacos.config.enabled", "未设置"));
    }
}
