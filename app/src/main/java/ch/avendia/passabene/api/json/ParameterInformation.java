
package ch.avendia.passabene.api.json;

import com.google.gson.annotations.Expose;

public class ParameterInformation {

    @Expose
    private String Name;
    @Expose
    private String Value;

    /**
     * 
     * @return
     *     The Name
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @param Name
     *     The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * 
     * @return
     *     The Value
     */
    public String getValue() {
        return Value;
    }

    /**
     * 
     * @param Value
     *     The Value
     */
    public void setValue(String Value) {
        this.Value = Value;
    }

}
