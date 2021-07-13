package cn.blue.rabbitmq.entity;

/**
 * @author Blue
 * @date 2020/8/30
 **/
public class UserInfo {

    private String userName;

    private Integer age;

    public UserInfo() {
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }

    public UserInfo(String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
