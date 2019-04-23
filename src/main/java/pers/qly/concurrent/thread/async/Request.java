package pers.qly.concurrent.thread.async;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 19:49 2019/2/25
 */
public class Request {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                '}';
    }
}
