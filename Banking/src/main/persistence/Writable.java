package persistence;

import org.json.JSONObject;

// Cite from JsonSerializationDemo.java
// Citation Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
