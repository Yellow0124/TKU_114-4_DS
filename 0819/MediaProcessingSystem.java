abstract class MediaFile {
    private final String fileName;
    private final double sizeMB;

    MediaFile(String fileName, double sizeMB) {
        this.fileName = (fileName == null || fileName.isBlank()) ? "unnamed_file" : fileName.trim();
        this.sizeMB = Math.max(0.0, sizeMB);
    }

    String getFileName() {
        return fileName;
    }

    double getSizeMB() {
        return sizeMB;
    }

    abstract String getMediaType();

    void displayInfo() {
        System.out.printf("[%s] 檔名: %-18s | 大小: %5.1f MB%n", getMediaType(), fileName, sizeMB);
    }
}

interface Playable {
    void play();
}

interface MediaCompressible {
    void compress(int qualityPercent);
}

class ImageFile extends MediaFile implements MediaCompressible {
    private final String resolution;

    ImageFile(String fileName, double sizeMB, String resolution) {
        super(fileName, sizeMB);
        this.resolution = (resolution == null || resolution.isBlank()) ? "1920x1080" : resolution.trim();
    }

    @Override
    String getMediaType() {
        return "Image";
    }

    @Override
    public void compress(int qualityPercent) {
        System.out.println("  -> [壓縮] 最佳化圖片解析度 (" + resolution + ")，壓縮品質設為 " + qualityPercent + "%");
    }
}

class AudioFile extends MediaFile implements Playable, MediaCompressible {
    private final int bitRateKbps;

    AudioFile(String fileName, double sizeMB, int bitRateKbps) {
        super(fileName, sizeMB);
        this.bitRateKbps = Math.max(64, bitRateKbps);
    }

    @Override
    String getMediaType() {
        return "Audio";
    }

    @Override
    public void play() {
        System.out.println("  -> [播放] 解碼音訊串流中... 位元率: " + bitRateKbps + " kbps");
    }

    @Override
    public void compress(int qualityPercent) {
        System.out.println("  -> [壓縮] 轉換音訊編碼格式，保留 " + qualityPercent + "% 音質");
    }
}

class VideoFile extends MediaFile implements Playable, MediaCompressible {
    private final String codec;

    VideoFile(String fileName, double sizeMB, String codec) {
        super(fileName, sizeMB);
        this.codec = (codec == null || codec.isBlank()) ? "H.264" : codec.trim();
    }

    @Override
    String getMediaType() {
        return "Video";
    }

    @Override
    public void play() {
        System.out.println("  -> [播放] 載入硬體解碼器 (" + codec + ") 播放高畫質視訊");
    }

    @Override
    public void compress(int qualityPercent) {
        System.out.println("  -> [壓縮] 重新編碼 " + codec + " 視訊軌與音訊軌，目標壓縮比: " + qualityPercent + "%");
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        System.out.println("================ 媒體檔案處理系統 ================\n");

        MediaFile[] files = {
                new ImageFile("banner.png", 4.2, "3840x2160"),
                new AudioFile("podcast_ep01.mp3", 45.8, 320),
                new VideoFile("concert_live.mp4", 1250.0, "HEVC/H.265")
        };

        for (MediaFile file : files) {
            file.displayInfo();

            if (file instanceof Playable playable) {
                playable.play();
            } else {
                System.out.println("  -> [播放] 此檔案格式不支援直接播放");
            }

            if (file instanceof MediaCompressible compressible) {
                compressible.compress(80);
            }

            System.out.println();
        }
    }
}