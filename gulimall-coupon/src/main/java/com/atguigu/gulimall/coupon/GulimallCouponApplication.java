//package com.atguigu.gulimall.coupon;
//
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.core.env.MapPropertySource;
//
///*
// * 1、如何使用Nacos作为配置中心统一管理配置
// * 1）、引入依赖，即nacos作为配置中心的依赖
// * 2）、创建一个bootstrap.properties，
// *
// * */
//@EnableDiscoveryClient
//@SpringBootApplication
////@MapperScan("com.atguigu.gulimall.coupon.dao")
//public class GulimallCouponApplication {
//
//    public static void main(String[] args) {
//        // 1. 【可选】设置详细日志级别，便于调试
//        System.setProperty("logging.level.com.alibaba.cloud.nacos", "DEBUG");
//        System.setProperty("logging.level.org.springframework.cloud.bootstrap", "DEBUG");
//
//        // 2. 启动Spring应用
//        ConfigurableApplicationContext context = SpringApplication.run(GulimallCouponApplication.class, args);
//
//        // 3. 【新增】启动后执行配置诊断
//        performConfigurationDiagnosis(context);
//    }
//
//    /**
//     * 配置诊断方法 - 用于检查Nacos配置是否生效
//     */
//    private static void performConfigurationDiagnosis(ConfigurableApplicationContext context) {
//        System.out.println("\n" + "=".repeat(80));
//        System.out.println("🔍 Nacos配置中心诊断报告");
//        System.out.println("=".repeat(80));
//
//        ConfigurableEnvironment env = context.getEnvironment();
//
//        // 诊断1：检查所有配置源
//        diagnosePropertySources(env);
//
//        // 诊断2：检查关键配置项
//        diagnoseKeyProperties(env);
//
//        // 诊断3：验证Nacos配置是否生效
//        validateNacosConfiguration(env);
//
//        // 诊断4：检查Nacos客户端状态
//        checkNacosClient(context);
//
//        System.out.println("\n" + "=".repeat(80));
//        System.out.println("诊断完成 - 应用正常运行中");
//        System.out.println("=".repeat(80));
//
//        // 提示用户如何验证
//        System.out.println("\n💡 验证步骤：");
//        System.out.println("1. 访问测试接口: http://localhost:8080/coupon/coupon/test");
//        System.out.println("2. 如果看到默认值(default-user/18)，说明Nacos配置未生效");
//        System.out.println("3. 如果看到Nacos中的值，说明配置中心生效");
//    }
//
//    /**
//     * 诊断1：检查所有配置源
//     */
//    private static void diagnosePropertySources(ConfigurableEnvironment env) {
//        System.out.println("\n📋 1. 所有配置源（按优先级从高到低）：");
//        System.out.println("-".repeat(60));
//
//        int index = 1;
//        boolean foundNacosSource = false;
//
//        for (org.springframework.core.env.PropertySource<?> source : env.getPropertySources()) {
//            String sourceName = source.getName();
//            System.out.printf("%2d. %s%n", index++, sourceName);
//
//            // 检查是否是Nacos配置源
//            String lowerName = sourceName.toLowerCase();
//            boolean isNacosSource = lowerName.contains("nacos") ||
//                    lowerName.contains("bootstrapproperties") ||
//                    sourceName.contains("bootstrapProperties");
//
//            if (isNacosSource) {
//                foundNacosSource = true;
//                System.out.println("    🔵 识别为Nacos配置源");
//
//                if (source instanceof MapPropertySource) {
//                    MapPropertySource mapSource = (MapPropertySource) source;
//                    int itemCount = mapSource.getSource().size();
//
//                    if (itemCount == 0) {
//                        System.out.println("    ❌ 问题：此配置源为空（没有配置项）");
//                        System.out.println("       可能原因：Nacos中的配置内容为空或未正确加载");
//                    } else {
//                        System.out.println("    ✅ 包含 " + itemCount + " 个配置项");
//
//                        // 显示所有配置项
//                        System.out.println("    配置项列表：");
//                        mapSource.getSource().forEach((key, value) -> {
//                            System.out.printf("        %-40s = %s%n", key, value);
//                        });
//                    }
//                }
//            }
//        }
//
//        if (!foundNacosSource) {
//            System.out.println("\n⚠️ 警告：未找到Nacos配置源！");
//            System.out.println("   可能原因：");
//            System.out.println("   1. bootstrap.properties配置错误");
//            System.out.println("   2. 缺少spring-cloud-starter-bootstrap依赖");
//            System.out.println("   3. Nacos配置中心未启用");
//        }
//    }
//
//    /**
//     * 诊断2：检查关键配置项
//     */
//    private static void diagnoseKeyProperties(ConfigurableEnvironment env) {
//        System.out.println("\n🔑 2. 关键配置项及其来源：");
//        System.out.println("-".repeat(60));
//
//        String[][] keyChecks = {
//                {"coupon.user.name", "业务配置 - 用户名"},
//                {"coupon.user.age", "业务配置 - 年龄"},
//                {"spring.application.name", "应用名称"},
//                {"spring.cloud.nacos.config.server-addr", "Nacos服务器地址"},
//                {"spring.cloud.nacos.config.file-extension", "配置文件扩展名"},
//                {"spring.cloud.nacos.config.group", "Nacos分组"},
//                {"spring.cloud.nacos.config.namespace", "Nacos命名空间"}
//        };
//
//        for (String[] check : keyChecks) {
//            String key = check[0];
//            String description = check[1];
//            String value = env.getProperty(key, "未设置");
//
//            System.out.printf("%-45s = %s%n", description + " (" + key + ")", value);
//
//            // 找出配置来源
//            boolean foundSource = false;
//            for (org.springframework.core.env.PropertySource<?> source : env.getPropertySources()) {
//                if (source.containsProperty(key)) {
//                    System.out.printf("%-45s   ↳ 来源: %s%n", "", source.getName());
//                    foundSource = true;
//                    break;
//                }
//            }
//
//            if (!foundSource && !"未设置".equals(value)) {
//                System.out.printf("%-45s   ↳ 来源: 未知%n", "");
//            }
//        }
//    }
//
//    /**
//     * 诊断3：验证Nacos配置是否生效
//     */
//    private static void validateNacosConfiguration(ConfigurableEnvironment env) {
//        System.out.println("\n✅ 3. Nacos配置生效验证：");
//        System.out.println("-".repeat(60));
//
//        String userName = env.getProperty("coupon.user.name", "未找到");
//        String userAge = env.getProperty("coupon.user.age", "未找到");
//
//        // 判断是否是Nacos配置
//        boolean isNacosConfig = false;
//        boolean isDefaultValue = false;
//
//        // 检查配置来源
//        for (org.springframework.core.env.PropertySource<?> source : env.getPropertySources()) {
//            if (source.containsProperty("coupon.user.name")) {
//                String sourceName = source.getName().toLowerCase();
//                if (sourceName.contains("nacos") || sourceName.contains("bootstrapproperties")) {
//                    isNacosConfig = true;
//                    break;
//                }
//            }
//        }
//
//        // 判断值是否为默认值
//        if ("default-user".equals(userName) || "default-name".equals(userName)) {
//            isDefaultValue = true;
//        }
//
//        System.out.println("coupon.user.name 当前值: " + userName);
//        System.out.println("coupon.user.age  当前值: " + userAge);
//
//        if (isNacosConfig) {
//            if (isDefaultValue) {
//                System.out.println("❌ 状态：Nacos配置源已找到，但值为默认值（配置可能为空）");
//            } else {
//                System.out.println("🎉 状态：Nacos配置生效成功！");
//                System.out.println("   当前使用的是Nacos配置中心的值");
//            }
//        } else {
//            System.out.println("⚠️ 状态：配置来自本地，Nacos配置未生效");
//            System.out.println("   可能原因：");
//            System.out.println("   1. Nacos配置未加载");
//            System.out.println("   2. 本地配置覆盖了Nacos配置");
//        }
//    }
//
//    /**
//     * 诊断4：检查Nacos客户端状态
//     */
//    private static void checkNacosClient(ConfigurableApplicationContext context) {
//        System.out.println("\n🔧 4. Nacos客户端状态检查：");
//        System.out.println("-".repeat(60));
//
//        try {
//            // 尝试获取Nacos相关Bean
//            Object nacosConfigProperties = context.getBean("nacosConfigProperties");
//            System.out.println("✅ NacosConfigProperties Bean: 存在");
//
//            // 检查配置服务
//            com.alibaba.cloud.nacos.NacosConfigManager nacosConfigManager =
//                    context.getBean(com.alibaba.cloud.nacos.NacosConfigManager.class);
//            System.out.println("✅ NacosConfigManager Bean: 存在");
//
//            // 尝试获取配置服务实例
//            try {
//                com.alibaba.nacos.api.config.ConfigService configService =
//                        nacosConfigManager.getConfigService();
//                System.out.println("✅ ConfigService实例: 正常");
//
//                // 尝试获取配置
//                String dataId = context.getEnvironment().getProperty("spring.application.name", "gulimall-coupon") + ".properties";
//                String group = context.getEnvironment().getProperty("spring.cloud.nacos.config.group", "DEFAULT_GROUP");
//                String content = configService.getConfig(dataId, group, 3000);
//
//                if (content == null || content.trim().isEmpty()) {
//                    System.out.println("⚠️ Nacos服务器返回的配置内容为空");
//                    System.out.println("   请检查Nacos控制台，Data ID: " + dataId);
//                } else {
//                    System.out.println("✅ Nacos服务器配置内容:");
//                    System.out.println("   Data ID: " + dataId);
//                    System.out.println("   Group: " + group);
//                    System.out.println("   内容长度: " + content.length() + " 字符");
//                    System.out.println("   内容预览: " + content.substring(0, Math.min(content.length(), 100)));
//                }
//
//            } catch (Exception e) {
//                System.out.println("❌ ConfigService异常: " + e.getMessage());
//            }
//
//        } catch (Exception e) {
//            System.out.println("❌ Nacos相关Bean未找到: " + e.getMessage());
//            System.out.println("   可能原因：");
//            System.out.println("   1. 依赖缺失");
//            System.out.println("   2. 自动配置被禁用");
//        }
//    }
//}

package com.atguigu.gulimall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
* 1、如何使用Nacos作为配置中心统一管理配置
* 1）、引入依赖，即nacos作为配置中心的依赖
* 2）、创建一个bootstrap.properties，
*
* */
@EnableDiscoveryClient
@SpringBootApplication
//@MapperScan("com.atguigu.gulimall.coupon.dao")
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
