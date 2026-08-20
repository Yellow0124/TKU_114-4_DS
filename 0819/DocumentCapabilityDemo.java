interface Exportable {
    String exportAsJson();
}

interface Compressible {
    byte[] compressToZip();
}

class BackupDocument implements Exportable, Compressible {
    private final String docId;
    private final String content;

    BackupDocument(String docId, String content) {
        this.docId = docId == null || docId.isBlank() ? "DOC000" : docId.trim();
        this.content = content == null ? "" : content.trim();
    }

    @Override
    public String exportAsJson() {
        return "{\"id\":\"" + docId + "\",\"content\":\"" + content + "\"}";
    }

    @Override
    public byte[] compressToZip() {
        return ("ZIP[" + docId + ":" + content + "]").getBytes();
    }

    String getDocId() {
        return docId;
    }
}

public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        BackupDocument doc = new BackupDocument("D101", "User activity log");

        Exportable exportableRef = doc;
        Compressible compressibleRef = doc;

        System.out.println("export=" + exportableRef.exportAsJson());
        System.out.println("compressed bytes=" + compressibleRef.compressToZip().length);

        System.out.println("same object (==): " + (exportableRef == compressibleRef));
        System.out.println("exportableRef instanceof Compressible: " + (exportableRef instanceof Compressible));
    }
}