import Entity.Organization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManagementSystem {
    public interface InOut {
        String getInput() throws IOException;

        void sendOutput(Object s);
    }

    Organization organization = new Organization();

    EmployeeModifier employeeModifier = new EmployeeModifier(organization);

    public void run(InOut inOut){

        EmployeeIterator prompts = new EmployeeIterator();
        List<String> temp = new ArrayList<>();

        inOut.sendOutput("Type 'exit' to quit or 'ok' to enter HR scheduling system");
        try{
            String input = inOut.getInput();
            while (!input.equals("exit") && prompts.hasNext()) {

                inOut.sendOutput(prompts.next());
                input = inOut.getInput();
                if(!input.equals("exit")){
                    temp.add(input);
                }

            }
            inOut.sendOutput(temp);
        } catch (IOException e) {
            inOut.sendOutput("something went wrong");
        }
        // actual wort stores here.

    }


}