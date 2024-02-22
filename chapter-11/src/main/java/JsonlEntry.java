public class JsonlEntry {

    private String instruction;

    private String output;

    public JsonlEntry(String instruction, String output) {
        this.instruction = instruction;
        this.output = output;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "JsonLEntry{" +
                "instruction='" + instruction + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}
