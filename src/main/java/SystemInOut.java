import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInOut implements ManagementSystem.InOut {

    BufferedReader reader;


    public SystemInOut() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getInput() throws IOException {
        return reader.readLine();
    }

    @Override
    public void sendOutput(Object s) {
        System.out.println(s);
    }
}
