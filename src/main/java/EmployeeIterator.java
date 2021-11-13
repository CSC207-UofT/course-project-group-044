import Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;

@Service
/**
 * A Manager has the power to modify employees in this Organization.
 * has permission to hire and fire Employee from the Organization, and evaluate payroll .
 *
 * @param properties  the database that stores and accesses all Employees.
 * @param current
 */
public class EmployeeIterator implements Iterator<String> {
    private List<String> properties = new ArrayList<>();
    private int current = 0;


    public EmployeeIterator() {

        //open file and read from it...
        BufferedReader br = null;
        try {
            Scanner myReader = new Scanner(new File("employee_properties.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                properties.add(data + ": ");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("employee_properties.txt is missing");
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return current < properties.size();
    }

    @Override
    public String next() {
        String res;


        try {
            res = properties.get(current);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        current += 1;
        return res;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }
}

