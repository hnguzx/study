package main.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.info.AudioInfo;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.process.ProcessLocator;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class FFmpegTest {

    File sourceFile = new File("D:\\KuGou\\筠子-立秋.wav");
    File waterMarkSourceFile = new File("D:\\KuGou\\李荣浩 - 贝贝.wav");
    File sourceFileMP3 = new File("D:\\KuGou\\筠子-立秋.mp3");
    File emptyFile = new File("D:\\KuGou\\emptyFile.wav");
    File resultFile = new File("D:\\KuGou\\result.wav");
    File waterMark = new File("D:\\KuGou\\李荣浩 - 贝贝.wav");
    File testVideo = new File("D:\\KuGou\\backup\\test.mp4");

    /**
     * 获取音频文件信息
     */
    @Test
    public void testGetFileInfo() throws EncoderException {
        File advFile = new File("D:\\KuGou\\watermark.wav");
        MultimediaObject musicFile = new MultimediaObject(advFile);
        MultimediaInfo info = musicFile.getInfo();

        String format = info.getFormat();
        Map<String, String> metadata = info.getMetadata();
        AudioInfo audio = info.getAudio();
        // 毫秒
        long duration = info.getDuration();
        log.info("format:{},metadata:{},duration:{}", format, metadata, duration);

        String decoder = audio.getDecoder();
        int bitRate = audio.getBitRate();
        int channels = audio.getChannels();
        int samplingRate = audio.getSamplingRate();
        Map<String, String> audioMetadata = audio.getMetadata();
        log.info("audio info: decoder:{},bitRate:{},channels:{},samplingRate:{},audioMetaData:{}", decoder, bitRate, channels, samplingRate, audioMetadata);
    }

    /**
     * 生成指定时长的空白音频文件
     */
    @Test
    public void testGenerateEmptyFile() throws IOException {
        // 指定音频参数
//        Encoding:音频编码类型，如PCMSIGNED、PCMUNSIGNED等。
//        采样率:每秒采样的个数，如44100、48000等。
//        样本量:每个样本的位数，例如8、16。
//        声道数:音频声道数，单声道为1，立体声为2。
//        帧大小:每帧音频的字节数，计算方法为样本量*通道/ 8。
//        帧速率:每秒的帧数，通常与采样速率相同。
//        字节序:音频数据的字节顺序，可以是大端序(true)，也可以是小端序(false)。
        AudioFormat audioFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                44100, 16, 2, 4, 44100, false);

        // 计算所需的字节数
        int durationInSeconds = 10;
        int numBytes = (int) (durationInSeconds * audioFormat.getFrameRate()
                * audioFormat.getFrameSize());

        // 创建一个字节数组，填充为0，代表静音
        byte[] buffer = new byte[numBytes];

        // 将字节数组包装成一个流
        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(buffer);

        // 创建音频流
        AudioInputStream audioInputStream =
                new AudioInputStream(byteArrayInputStream, audioFormat, buffer.length / audioFormat.getFrameSize());

        // 写入新的音频文件
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, emptyFile);
    }


    /**
     * 音频文件格式转为.wav
     */
    @Test
    public void testFormatConvertToWav() {
        try {
            // Open the WAV file
            File mp3File = new File("path/to/output.mp3");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(mp3File);

            // Set the output format to MP3
            AudioFormat format = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100, // sample rate
                    16, // sample size in bits
                    2, // number of channels
                    4, // frame size
                    44100, // frame rate
                    true // byte order (true for big-endian)
            );
            AudioInputStream convertedAudioInputStream = AudioSystem.getAudioInputStream(format, audioInputStream);

            // Write the MP3 file
            File wavFile = new File("path/to/input.wav");
            FileOutputStream outputStream = new FileOutputStream(wavFile);
            AudioSystem.write(convertedAudioInputStream, AudioFileFormat.Type.WAVE, outputStream);

            // Close the streams
            audioInputStream.close();
            convertedAudioInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 音频格式转为.mp3
     */
    @Test
    public void testFormatConvertToMp3() throws EncoderException {
        MultimediaObject music = new MultimediaObject(sourceFile);

        AudioAttributes audioAttributes = new AudioAttributes();
        Optional<String> codec = audioAttributes.getCodec();
        codec.ifPresent(s -> log.info("codec:{}", s));
        // LAME
        audioAttributes.setCodec("libmp3lame");
//        audioAttributes.setVolume(100);
        audioAttributes.setChannels(2);
        audioAttributes.setBitRate(128000);
        audioAttributes.setSamplingRate(44100);

        EncodingAttributes encodingAttributes = new EncodingAttributes();
//        encodingAttributes.setInputFormat("wav");
        encodingAttributes.setOutputFormat("mp3");
        encodingAttributes.setAudioAttributes(audioAttributes);

        Encoder encoder = new Encoder();
        String[] supportedDecodingFormats = encoder.getSupportedDecodingFormats();
        String[] supportedEncodingFormats = encoder.getSupportedEncodingFormats();
        log.info("supportedDecodingFormats:{}", Arrays.toString(supportedDecodingFormats));
        log.info("supportedEncodingFormats:{}", Arrays.toString(supportedEncodingFormats));

        encoder.encode(music, sourceFileMP3, encodingAttributes);
    }

    /**
     * 获取文件信息
     *
     * @throws IOException
     */
    @Test
    public void testGetFileInfo2() throws IOException {
        ProcessLocator locator = new DefaultFFMPEGLocator();
        ProcessWrapper executor = locator.createExecutor();
        executor.addArgument("-i");
        executor.addArgument("D:\\KuGou\\筠子-立秋.wav");
        executor.execute();

        String info = IOUtils.toString(executor.getErrorStream(), StandardCharsets.UTF_8);
        log.info("file info:{}", info);

    }

    @Test
    public void testAudioWaterMark() throws UnsupportedAudioFileException, IOException, InterruptedException {
        // Load audio file
        File audioFile = sourceFile;
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        byte[] audioBytes = new byte[(int) audioFile.length()];
        audioInputStream.read(audioBytes);

        // Load watermark file
        File watermarkFile = waterMark;
        AudioInputStream watermarkInputStream = AudioSystem.getAudioInputStream(watermarkFile);
        byte[] watermarkBytes = new byte[(int) watermarkFile.length()];
        watermarkInputStream.read(watermarkBytes);

        // Insert watermark into audio file
        int watermarkIndex = 0;
        // 覆盖原音频
        /*for (int i = 44; i < audioBytes.length; i += 2) {
            if (watermarkIndex >= watermarkBytes.length) {
                break;
            }
            audioBytes[i] = watermarkBytes[watermarkIndex];
            audioBytes[i + 1] = watermarkBytes[watermarkIndex + 1];
            watermarkIndex += 2;
        }*/

        // Mix watermark with audio file
        // 单次，在开始位置添加水印
        /*for (int i = 44; i < audioBytes.length; i += 2) {
            if (watermarkIndex >= watermarkBytes.length) {
                break;
            }
            short audioSample = (short) ((audioBytes[i] & 0xff) | (audioBytes[i + 1] << 8));
            short watermarkSample = (short) ((watermarkBytes[watermarkIndex] & 0xff) | (watermarkBytes[watermarkIndex + 1] << 8));
            short mixedSample = (short) ((audioSample * 0.2) + (watermarkSample * 0.8));
            audioBytes[i] = (byte) (mixedSample & 0xff);
            audioBytes[i + 1] = (byte) ((mixedSample >> 8) & 0xff);
            watermarkIndex += 2;
        }*/

        // 无间隔循环添加水印
        /*for (int i = 44; i < audioBytes.length; i += 2) {
            short audioSample = (short) ((audioBytes[i] & 0xff) | (audioBytes[i + 1] << 8));
            short watermarkSample = (short) ((watermarkBytes[watermarkIndex] & 0xff) | (watermarkBytes[watermarkIndex + 1] << 8));
            short mixedSample = (short) ((audioSample * 0.3) + (watermarkSample * 0.7));
            audioBytes[i] = (byte) (mixedSample & 0xff);
            audioBytes[i + 1] = (byte) ((mixedSample >> 8) & 0xff);
            watermarkIndex += 2;
            if (watermarkIndex >= watermarkBytes.length) {
                watermarkIndex = 0;
            }
        }*/

        // 指定间隔循环添加水印
        int watermarkInterval = (int) (audioInputStream.getFormat().getFrameRate() * audioInputStream.getFormat().getFrameSize() * 5); // 每个水印之间的间隔，以帧为单位

        for (int i = 44; i < audioBytes.length; i += 2) { // 从第一个音频样本开始遍历，每次跳过两个字节（因为每个样本是两个字节）
            short audioSample = (short) ((audioBytes[i] & 0xff) | (audioBytes[i + 1] << 8)); // 从音频数据中获取一个样本
            short watermarkSample = (short) ((watermarkBytes[watermarkIndex] & 0xff) | (watermarkBytes[watermarkIndex + 1] << 8)); // 从水印数据中获取一个样本
            short mixedSample = (short) ((audioSample * 0.7) + (watermarkSample * 0.3)); // 将音频样本和水印样本混合

            audioBytes[i] = (byte) (mixedSample & 0xff); // 将混合后的样本写回音频数据中
            audioBytes[i + 1] = (byte) ((mixedSample >> 8) & 0xff); // 将混合后的样本写回音频数据中

            watermarkIndex += 2; // 移动到下一个水印样本
            if (watermarkIndex >= watermarkBytes.length) { // 如果已经处理完了所有的水印样本
                watermarkIndex = 0; // 重新从第一个水印样本开始
                int watermarkEndIndex = i; // 当前水印的结束位置
                int nextWatermarkStartIndex = watermarkEndIndex + watermarkInterval; // 下一个水印的开始位置
                for (int j = watermarkEndIndex; j < nextWatermarkStartIndex && j < audioBytes.length; j += 2) { // 将上一个水印和当前水印之间的样本设置为静音
                    short audioSampleSilent = (short) ((audioBytes[j] & 0xff) | (audioBytes[j + 1] << 8)); // 从音频数据中获取一个样本
                    audioBytes[j] = (byte) (audioSampleSilent & 0xff); // 将样本设置为静音
                    audioBytes[j + 1] = (byte) ((audioSampleSilent >> 8) & 0xff); // 将样本设置为静音
                }
                i = nextWatermarkStartIndex - 2; // 跳转到下一个水印的开始位置
            }
        }

        // Write mixed audio file
        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
        AudioInputStream mixedAudioInputStream = new AudioInputStream(bais, audioInputStream.getFormat(), audioBytes.length);
        AudioSystem.write(mixedAudioInputStream, AudioFileFormat.Type.WAVE, resultFile);
    }

    @Test
    public void codecMp4() {
        AudioAttributes audioAttributes = new AudioAttributes();
        audioAttributes.setChannels(2);
        audioAttributes.setCodec("aac");

    }

    @Test
    public void testVideo() throws IOException {
        ProcessLocator locator = new DefaultFFMPEGLocator();
        ProcessWrapper executor = locator.createExecutor();

        executor.addArgument("-i");
        String absolutePath = testVideo.getAbsolutePath();
        executor.addArgument(absolutePath);
        executor.addArgument("-acodec");
        executor.addArgument("copy");
        executor.addArgument("-vn");
        executor.addArgument("D:\\KuGou\\backup\\audio.mp4");

        executor.execute();
        executor.destroy();
    }
}
