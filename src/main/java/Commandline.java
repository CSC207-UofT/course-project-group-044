
public class Commandline {

    public static void main(String[] args) {

        ManagementSystem em = new ManagementSystem();
        SystemInOut inOut = new SystemInOut();
        em.run(inOut);

    }
}
