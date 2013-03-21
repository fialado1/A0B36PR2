/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;

/**
 *
 * @author Dominik
 */

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * Trida slouzici k prehravani hudebnich souboru, a to predevsim, kdyz hrac pouzije predmet funkcni mpTrojka
 * v budoucnu planuji naprogramovani moznosti prepinani mezi skladbami, i kdyz ta momentalne prehravana nedohrala (vlakna -> interupt)
 * 
 */
public class Music {
     public void hrej(String nazevWav) {
        SourceDataLine soundLine = null;
        int BUFFER_SIZE = 64 * 1024; //64KB
        // Set up an audio input stream piped from the sound file.
        try {
            File soundFile = new File(nazevWav + ".wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
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
    }    
}
