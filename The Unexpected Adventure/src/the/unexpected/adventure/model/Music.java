package the.unexpected.adventure.model;

/**
 *
 * @author Dominik
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Trida slouzici k prehravani hudebnich souboru, a to predevsim, kdyz hrac
 * pouzije predmet funkcni mpTrojka v budoucnu planuji naprogramovani moznosti
 * prepinani mezi skladbami, i kdyz ta momentalne prehravana nedohrala (vlakna
 * -> interupt)
 *
 */
public class Music implements Runnable {

    Thread vlakno = new Thread(this);
    private String[] playlist;
    private boolean playing = true;
    Music.Node current;

    public Music() throws UnsupportedAudioFileException, LineUnavailableException {
        nactiData();
    }

    public void startThread() {
        vlakno.start();
    }

    @Override
    public void run() {
        hrejPrehravac();
    }

    public final void nactiData() {
        File file = new File("hudba/wav/playlistWAV.txt");

        if (!file.exists()) {
            System.out.println("Soubor playlist neexistuje!");
            return;
        }

        if (!(file.isFile() && file.canRead())) {
            System.out.println("Nelze nacitat data z " + file.getName());
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(file);

            char c1;
            do {
                c1 = (char) fis.read();
            } while (!(c1 >= ' ' && c1 <= 'z'));
            int pocetPisni = Character.getNumericValue(c1); //konverze z char na int
            playlist = new String[pocetPisni];


            for (int i = 0; i < playlist.length; i++) {
                char ch1;
                do {
                    ch1 = (char) fis.read();             //nacteni znaku
                    if (ch1 == 13) {
                        playlist[i] = "";
                    }
                    //aby se v poli nevyskytovalo "null" - pro prvni znak se pole primo rovna znaku
                    if (playlist[i] == null && ch1 != '#') {
                        playlist[i] = "" + ch1;
                    } else {
                        if (ch1 != '#') {
                            if (ch1 >= ' ' && ch1 <= 'z') {
                                playlist[i] += ch1;
                            }
                        }
                    }
                } while (ch1 != '#');

                addNode(playlist[i]);
            }
        } catch (IOException e) {
        }
//        for (int i = 0; i < playlist.length; i++) {
//            addNode(playlist[i]);
//        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void hrejPrehravac() {
        SourceDataLine soundLine = null;
        int BUFFER_SIZE = 64 * 1024; //64KB

        // Set up an audio input stream piped from the sound file.
        while (isPlaying()) {
            try {
                File soundFileCurr = new File("hudba/wav/" + current.getData() + ".wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileCurr);
                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                soundLine = (SourceDataLine) AudioSystem.getLine(info);
                soundLine.open(audioFormat);
                soundLine.start();
                int nBytesRead = 0;
                byte[] sampledData = new byte[BUFFER_SIZE];
                while (nBytesRead != -1) {
                    nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
                    if (nBytesRead >= 0) {
                        // Writes audio data to the mixer via this source data line.
                        soundLine.write(sampledData, 0, nBytesRead);
                    }
                }

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            } finally {
                soundLine.drain();
                soundLine.close();

            }
            if (!soundLine.isRunning()) {
                forward();
            }

        }

    }

    public void addNode(String data) {
        if (current == null) {
            current = new Music.Node(data);
            current.prev = current;
            current.next = current;
        } else {
            current.next = new Music.Node(data, current, current.next);
            if (current.prev == current) {
                current.prev = current.next;
            } else {
                current.next.next.prev = current.next;
            }
        }
    }

    public void forward() {
        if (current != null) {
            Music.Node pomoc = current;
            current = current.prev;
            current.prev.prev.next = current.prev;
            current.next = pomoc;
        }
    }

    public void reverse() {
        if (current != null) {
            Music.Node pomoc = current;
            current = current.next;
            current.prev = pomoc;
            current.next.next.prev = current.next;
        }
    }

    /**
     * spojovy seznam slouzici jako playlist, cimz je mozne prehrat jak
     * nasledujici tak i predchozi pisnicku
     */
    private class Node {

        private String data;
        private Music.Node prev;
        private Music.Node next;

        public Node(String data) {
            this.data = data;
            next = null;
        }

        public Node(String data, Music.Node prev, Music.Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public String getData() {
            return data;
        }

        public Music.Node getNext() {
            return next;
        }

        public Music.Node getPrev() {
            return prev;
        }

        public void setPrev(Music.Node prev) {
            this.prev = prev;
        }

        public void setNext(Music.Node next) {
            this.next = next;
        }
    }
}
