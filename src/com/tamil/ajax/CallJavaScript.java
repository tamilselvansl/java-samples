package com.tamil.ajax;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: Tamil
 * Date: Jan 13, 2011
 * Time: 6:31:45 PM
 */

public class CallJavaScript {
    public static void main(String arg[]) {
        String script_file = "script.js";

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            FileReader reader = new FileReader(script_file);
            engine.eval(reader);
            engine.eval("print(new Date())");
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ScriptException se) {
            se.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
