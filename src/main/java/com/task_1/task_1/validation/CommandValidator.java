package com.task_1.task_1.validation;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component // Tells Spring to manage this class as a bean
public class CommandValidator {

    //field to store data

    private static final List<String> COMMAND_BLACKLIST=Arrays.asList(
        "rm", "mkfs", "dd", "shutdown", "reboot", "poweroff", 
        "wget", "curl", "chmod", "chown", 
        ";", "&&", "||", "`", "$(,"


    );
    //function or method to do the logic

    public boolean isCommandSafe(String command){
        if (command==null || command.trim().isEmpty()){
            return false;//for empty command
        }// for something like "  "
        String lowerCaseCommand=command.toLowerCase();
        String[] parts=lowerCaseCommand.split("\\s+"); //for any whitw spaces, parts array will store the string
        for(String part:parts){
            if(COMMAND_BLACKLIST.contains(part)){
                return false;
            } //for each string part in our parts array 

        }
        return true;
    }
    
}
