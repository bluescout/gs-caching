package hello;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CallsCounter {
    
    private Map<String, Integer> calls = new HashMap<>();

    public void addCall(String methodName) {
        Integer count = calls.get(methodName);
        if (count==null) {
            calls.put(methodName, 1);
        } else {
            calls.put(methodName, count+1);
        }
    }
    
    public Integer getCalls(String methodName) {
        return calls.get(methodName);
    }
    
    public Map<String, Integer> getAllCalls() {
        return calls;
    }
}
