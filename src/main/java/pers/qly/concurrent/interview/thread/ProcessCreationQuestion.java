package pers.qly.concurrent.interview.thread;

import java.io.IOException;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 11:23 2019/4/12
 */
public class ProcessCreationQuestion {

    // Q：如何创建进程？
    // A：通过 Runtime 以及 Process

    public static void main(String[] args) throws IOException {

        // 获取 Java Runtime
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd /k start http://www.baidu.com");
        process.exitValue();
    }
}
