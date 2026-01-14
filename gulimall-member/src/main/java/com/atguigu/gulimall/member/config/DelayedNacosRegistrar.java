package com.atguigu.gulimall.member.config;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DelayedNacosRegistrar implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired(required = false)
    private NacosAutoServiceRegistration nacosAutoServiceRegistration;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (nacosAutoServiceRegistration != null) {
            System.out.println("=== 开始延迟注册到Nacos ===");

            // 创建一个新线程来执行延迟注册
            Thread registrationThread = new Thread(() -> {
                try {
                    // 延迟20秒，确保所有组件都已完全初始化
                    System.out.println("等待20秒确保所有组件初始化完成...");
                    Thread.sleep(20000);

                    System.out.println("正在执行Nacos注册...");

                    // 检查当前状态
                    System.out.println("Nacos客户端状态检查...");

                    // 手动触发注册
                    if (!nacosAutoServiceRegistration.isRunning()) {
                        System.out.println("启动Nacos注册...");
                        nacosAutoServiceRegistration.start();
                        System.out.println("✅ Nacos注册成功!");

                        // 打印注册信息
                        System.out.println("服务名称: gulimall-member");
                        System.out.println("服务端口: 8000");
                        System.out.println("Nacos地址: 127.0.0.1:8848");
                    } else {
                        System.out.println("⚠️ Nacos注册已在运行中");
                    }

                } catch (InterruptedException e) {
                    System.err.println("❌ 注册线程被中断: " + e.getMessage());
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.err.println("❌ Nacos延迟注册失败: " + e.getMessage());
                    e.printStackTrace();
                }
            }, "nacos-delayed-registration");

            // 设置为守护线程，避免阻止应用关闭
            registrationThread.setDaemon(true);
            registrationThread.start();

        } else {
            System.err.println("❌ NacosAutoServiceRegistration 未找到，请检查Nacos配置");
        }
    }
}