package EHMS;
import java.sql.*;
import java.util.*;

public class DBTablePrinter {

    private static final int DEFAULT_MAX_ROWS = 100;
    private static final int DEFAULT_MAX_TEXT_COL_WIDTH = 150;
    private static final int DEFAULT_ARRAY_SIZE = 10;
    private static final int MAX_TABLE_NAMES = 20;

    public static final int CATEGORY_STRING = 1;
    public static final int CATEGORY_INTEGER = 2;
    public static final int CATEGORY_DOUBLE = 3;
    public static final int CATEGORY_DATETIME = 4;
    public static final int CATEGORY_BOOLEAN = 5;
    public static final int CATEGORY_OTHER = 0;

    private static class Column {
        private String label;
        private int type;
        private String typeName;
        private int width = 0;
        private String[] values;
        private int valuesCount = 0;
        private String justifyFlag = "";
        private int typeCategory = 0;

        public Column(String label, int type, String typeName) {
            this.label = label;
            this.type = type;
            this.typeName = typeName;
            this.values = new String[DEFAULT_ARRAY_SIZE];
        }

        public String getLabel() {
            return label;
        }

        public int getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void addValue(String value) {
            if (valuesCount >= values.length) {
                values = resizeArray(values);
            }
            values[valuesCount++] = value;
        }

        public String getValue(int i) {
            return values[i];
        }

        private String[] resizeArray(String[] oldArray) {
            String[] newArray = new String[oldArray.length * 2];
            System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
            return newArray;
        }

        public String getJustifyFlag() {
            return justifyFlag;
        }

        public void justifyLeft() {
            this.justifyFlag = "-";
        }

        public int getTypeCategory() {
            return typeCategory;
        }

        public void setTypeCategory(int typeCategory) {
            this.typeCategory = typeCategory;
        }
    }

    public static void printTable(Connection conn, String tableName) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
        printResultSet(rs, DEFAULT_MAX_TEXT_COL_WIDTH);
        rs.close();
        stmt.close();
    }
    

    public static void printResultSet(ResultSet rs, int maxStringColWidth) {
        try {
            if (rs == null) {
                System.err.println("DBTablePrinter Error: Result set is null!");
                return;
            }
            if (rs.isClosed()) {
                System.err.println("DBTablePrinter Error: Result Set is closed!");
                return;
            }
            if (maxStringColWidth < 1) {
                System.err.println("DBTablePrinter Info: Invalid max. varchar column width. Using default!");
                maxStringColWidth = DEFAULT_MAX_TEXT_COL_WIDTH;
            }

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Column[] columns = new Column[columnCount];

            for (int i = 0; i < columnCount; i++) {
                Column c = new Column(rsmd.getColumnLabel(i + 1),
                                      rsmd.getColumnType(i + 1), 
                                      rsmd.getColumnTypeName(i + 1));
                c.setWidth(c.getLabel().length());
                c.setTypeCategory(whichCategory(c.getType()));
                columns[i] = c;
            }

            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    Column c = columns[i];
                    String value = rs.getString(i + 1);
                    if (value == null) value = "NULL";
                    c.addValue(value);
                }
            }

            StringBuilder headerLine = new StringBuilder();
            StringBuilder separatorLine = new StringBuilder();
            for (int i = 0; i < columnCount; i++) {
                Column c = columns[i];
                int width = Math.max(c.getWidth(), c.getLabel().length());

                String headerFormat = "| %-" + width + "s ";
                headerLine.append(String.format(headerFormat, c.getLabel()));
                separatorLine.append("+");
                for (int j = 0; j < width + 2; j++) {
                    separatorLine.append("-");
                }
            }
            headerLine.append("|");
            separatorLine.append("+");

            System.out.println(separatorLine);
            System.out.println(headerLine);
            System.out.println(separatorLine);

            for (int i = 0; i < columns[0].valuesCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    Column c = columns[j];
                    String valueFormat = "| %-" + Math.max(c.getWidth(), c.getLabel().length()) + "s ";
                    System.out.print(String.format(valueFormat, c.getValue(i)));
                }
                System.out.println("|");
            }

            System.out.println(separatorLine);
        } catch (SQLException e) {
            System.err.println("SQL exception in DBTablePrinter. Message:");
            System.err.println(e.getMessage());
        }
    }

    private static int whichCategory(int type) {
        switch (type) {
            case Types.BIGINT:
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                return CATEGORY_INTEGER;

            case Types.REAL:
            case Types.DOUBLE:
            case Types.DECIMAL:
                return CATEGORY_DOUBLE;

            case Types.DATE:
            case Types.TIME:
            case Types.TIME_WITH_TIMEZONE:
            case Types.TIMESTAMP:
            case Types.TIMESTAMP_WITH_TIMEZONE:
                return CATEGORY_DATETIME;

            case Types.BOOLEAN:
                return CATEGORY_BOOLEAN;

            case Types.VARCHAR:
            case Types.NVARCHAR:
            case Types.LONGVARCHAR:
            case Types.LONGNVARCHAR:
            case Types.CHAR:
            case Types.NCHAR:
                return CATEGORY_STRING;

            default:
                return CATEGORY_OTHER;
        }
    }
}