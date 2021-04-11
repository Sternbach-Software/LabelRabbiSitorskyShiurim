import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class Main {
    public static void main(String[] args) throws InterruptedException, CannotWriteException, IOException, CannotReadException, ReadOnlyFileException, InvalidAudioFrameException, TagException {
File[] sitorsky = new File("C:\\Users\\Shmuel\\OneDrive\\Desktop\\Shiurim to format for torahdownloads\\Rabbi Sitorsky").listFiles();
        assert sitorsky != null;
        tagMp3s(sitorsky);
    }

    public static void tagMp3s(File[] files) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException, InterruptedException {
        for (File file : files) {
            if (!file.isFile()) continue;
            String[] bits = file.getName().split("\\.");
            String parsha=file.getName().split("_")[0];
            String year=file.getName().split("_")[1].split("\\.")[0];
            boolean endsInMp3 = bits[bits.length - 1].equalsIgnoreCase("mp3");
            if (bits.length > 0 && endsInMp3) {
                MP3File f = (MP3File) AudioFileIO.read(file);
                Tag tag = f.getTag();
                if (tag.getFirst(FieldKey.TITLE) != null) {
                    tag.deleteField(FieldKey.TITLE);
                }
                tag.setField(FieldKey.TITLE, "Parshas "+parsha+" "+year);
                if (tag.getFirst(FieldKey.ALBUM) != null) {
                    tag.deleteField(FieldKey.ALBUM);
                }
                tag.setField(FieldKey.ALBUM,parsha);
                if (tag.getFirst(FieldKey.CONDUCTOR) != null) {
                    tag.deleteField(FieldKey.CONDUCTOR);
                }
                tag.setField(FieldKey.CONDUCTOR,"Parsha "+year);
                f.commit();
            }
        }
    }
}
