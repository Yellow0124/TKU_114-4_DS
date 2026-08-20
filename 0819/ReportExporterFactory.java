import java.util.Arrays;

interface ReportExporter {
    String getFormatName();

    String export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public String getFormatName() {
        return "CSV";
    }

    @Override
    public String export(String title, int[] values) {
        String safeTitle = (title == null || title.isBlank()) ? "Untitled" : title.trim();
        StringBuilder sb = new StringBuilder();
        sb.append("Title,Values\n").append(safeTitle).append(",");
        if (values == null || values.length == 0) {
            sb.append("\"\"");
        } else {
            sb.append("\"");
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]).append(i < values.length - 1 ? ";" : "");
            }
            sb.append("\"");
        }
        return sb.toString();
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public String getFormatName() {
        return "JSON";
    }

    @Override
    public String export(String title, int[] values) {
        String safeTitle = (title == null || title.isBlank()) ? "Untitled" : title.trim();
        String jsonArray = (values == null) ? "[]" : Arrays.toString(values);
        return String.format("{\"title\":\"%s\",\"values\":%s}", safeTitle, jsonArray);
    }
}

class TextExporter implements ReportExporter {
    @Override
    public String getFormatName() {
        return "TEXT";
    }

    @Override
    public String export(String title, int[] values) {
        String safeTitle = (title == null || title.isBlank()) ? "Untitled" : title.trim();
        String safeValues = (values == null || values.length == 0) ? "No data" : Arrays.toString(values);
        return String.format("=== [%s Report] ===\nTitle: %s\nData : %s\n====================",
                getFormatName(), safeTitle, safeValues);
    }
}

public class ReportExporterFactory {
    public static ReportExporter createExporter(String format) {
        if (format == null) {
            return new TextExporter();
        }
        return switch (format.trim().toLowerCase()) {
            case "csv" -> new CsvExporter();
            case "json" -> new JsonExporter();
            default -> new TextExporter();
        };
    }

    public static void exportReport(ReportExporter exporter, String title, int[] values) {
        if (exporter == null) {
            exporter = new TextExporter();
        }
        System.out.printf(">>> 使用 [%s] 格式匯出結果：%n", exporter.getFormatName());
        System.out.println(exporter.export(title, values));
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("================ 報表輸出 Factory 系統 ================\n");

        int[] sampleData = { 120, 350, 480, 210 };

        ReportExporter csvExp = createExporter("csv");
        exportReport(csvExp, "Q1 Sales", sampleData);

        ReportExporter jsonExp = createExporter("json");
        exportReport(jsonExp, "Q1 Sales", sampleData);

        ReportExporter textExp = createExporter("text");
        exportReport(textExp, "Q1 Sales", sampleData);

        ReportExporter fallbackExp = createExporter("xml");
        exportReport(fallbackExp, "Unsupported Format Test", sampleData);

        ReportExporter nullDataExp = createExporter("json");
        exportReport(nullDataExp, "Empty Metrics", null);
    }
}