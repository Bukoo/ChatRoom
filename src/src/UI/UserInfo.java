package src.UI;

public class UserInfo {
    private static Boolean flag;
    private static String ip;
    private static int portNum;

    public UserInfo() {
        flag = true;
        ip = "";
        portNum = 0;
    }

    public static void setFlag(Boolean flag_) { flag = flag_; }
    public Boolean getFlag() { return flag; }

    public static void setIp(String ip_) { ip = ip_; }
    public String getIp() { return ip; }

    public static void setPortNum(int portNum_) { portNum = portNum_; }
    public int getPortNum() { return portNum; }
}
