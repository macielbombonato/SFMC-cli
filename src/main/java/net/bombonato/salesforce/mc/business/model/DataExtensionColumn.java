package net.bombonato.salesforce.mc.business.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DataExtensionColumn {
    private String id = null;
    private String key = null;
    private String name = null;
    private String description = null;
    private Date createdDate = null;
    private Date modifiedDate = null;
    private Type type = null;
    private String defaultValue = null;
    private Boolean isPrimaryKey = null;
    private Boolean isRequired = null;
    private Integer length = null;
    private Integer precision = null;
    private Integer scale = null;

//    private ETDataExtension dataExtension = null;

    /**
     *  Data Extension Column Types that can be used
     */
    public enum Type {
        BOOLEAN("Boolean"),
        DATE("Date"),
        DECIMAL("Decimal"),
        EMAIL_ADDRESS("EmailAddress"),
        LOCALE("Locale"),
        NUMBER("Number"),
        PHONE("Phone"),
        TEXT("Text");
        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        public static Type fromValue(String value) {
            for (Type v : Type.values()) {
                if (v.value.equals(value)) {
                    return v;
                }
            }
            throw new IllegalArgumentException(value);
        }
    }
}
