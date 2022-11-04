package main.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import java.io.*;

@Slf4j
public class MusicTest {

    File sourceFile = null;
    File advFile = null;
    File resultFile = null;
    File waterMark = null;

    @BeforeEach
    void init() {
        sourceFile = new File("D:\\KuGou\\筠子-立秋.mp3");
        advFile = new File(("D:\\KuGou\\empty-10.mp3"));
        resultFile = new File(("D:\\KuGou\\result.mp3"));
        waterMark = new File(("D:\\KuGou\\watermark.mp3"));

    }

    /**
     * 音频拼接
     *
     * @throws IOException
     */
    @Test
    void testMp3() throws IOException {
        // 输入音频
        FileInputStream sourceStream = new FileInputStream(waterMark);
        long fileSize = waterMark.length();
        // 4,703,458
        long tag = fileSize / 1024 / 5;
        log.info("file size : {}, all adv times:{}", fileSize, tag);

        FileInputStream targetStream = new FileInputStream(advFile);

        BufferedInputStream music1 = new BufferedInputStream(sourceStream);
        BufferedInputStream music2 = new BufferedInputStream(targetStream);
        // 输出音频
        File filePlus = new File("D:\\KuGou\\plus.mp3");
        FileOutputStream fileOutputStream = new FileOutputStream(filePlus, true);
        BufferedOutputStream music3 = new BufferedOutputStream(fileOutputStream);

        byte[] bytes = new byte[1024];
        int sourceLength, advLength;
        while ((sourceLength = music1.read(bytes)) != -1) {
            music3.write(bytes, 0, sourceLength);
            music3.flush();
        }

        while ((advLength = music2.read(bytes)) != -1) {
            music3.write(new byte[1024], 0, advLength);
            music3.flush();
        }
        music1.close();
        music2.close();
        music3.close();
    }

    /**
     * 格式转换
     *
     * @throws EncoderException
     */
    @Test
    void testAac2Mp3() throws EncoderException {
        File advFile = new File(("D:\\KuGou\\adv.aac"));
        MultimediaObject music1 = new MultimediaObject(advFile);
        File targetFile = new File("D:\\KuGou\\adv.mp3");
        AudioAttributes audioAttributes = new AudioAttributes();
        Encoder encoder = new Encoder();
        audioAttributes.setCodec("libmp3lame");
        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setOutputFormat("mp3");
        encodingAttributes.setAudioAttributes(audioAttributes);
        encoder.encode(music1, targetFile, encodingAttributes);
    }

    @Test
    void testMusic2() throws EncoderException, IOException {
        MultimediaObject multimediaObject = new MultimediaObject(sourceFile);
        log.info("时长:{},音道数：{}，比特率：{}，采样比特率：{}",
                multimediaObject.getInfo().getDuration(),
                multimediaObject.getInfo().getAudio().getChannels(),
                multimediaObject.getInfo().getAudio().getBitRate(),
                multimediaObject.getInfo().getAudio().getSamplingRate());
    }

    private final static String WATERMARK_FILE = "D:\\KuGou\\watermark.mp3";


    /**
     * 生成指定时长的空白音频文件
     */
    @Test
    void generateBlankFile() {
        int seconds = 10;
        DefaultFFMPEGLocator ffmpegLocator = new DefaultFFMPEGLocator();
        ProcessWrapper executor = ffmpegLocator.createExecutor();
        String emptyAudioPath = "D:\\KuGou\\empty-" + seconds + ".mp3";
        File folder = new File("D:\\KuGou\\筠子-立秋.mp3");

        executor.addArgument("-ar");
        executor.addArgument("48000");
        executor.addArgument("-t");
        executor.addArgument("00:00:" + seconds);
        executor.addArgument("-f");
        executor.addArgument("s16le");
        executor.addArgument("-acodec");
        executor.addArgument("pcm_s16le");
        executor.addArgument("-i");
        executor.addArgument(folder.getAbsolutePath());
//        executor.addArgument("-f");
//        executor.addArgument("mp3");
        executor.addArgument("-filter_complex");
        executor.addArgument("[1:a][0:a]amix");
        executor.addArgument("-t");
        executor.addArgument(10 + "");
        executor.addArgument("-ar");
        executor.addArgument("48000");
        executor.addArgument("-f");
        executor.addArgument("mp3");
        executor.addArgument("-y");
        executor.addArgument(emptyAudioPath);

        BufferedReader br = null;
        try {
            executor.execute();
            br = new BufferedReader(new InputStreamReader(executor.getErrorStream()));
            String line;
            while ((line = br.readLine()) != null) {
                //输出处理过程中的日志（辅助观察处理过程）
                log.info(line);
            }
            log.info("「{}秒」静音音频生成完成！", seconds);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 将两个文件拼接
     *
     * @param seconds
     */
    @Test
    void generateLoopFile() {
        int seconds = 10;
        DefaultFFMPEGLocator ffmpegLocator = new DefaultFFMPEGLocator();
        ProcessWrapper executor = ffmpegLocator.createExecutor();

        String emptyAudioPath = "D:\\KuGou\\empty-" + seconds + ".mp3";
        String loop = "D:\\KuGou\\loop-" + seconds + ".mp3";

        executor.addArgument("-i");
        executor.addArgument(WATERMARK_FILE);
        executor.addArgument("-i");
        executor.addArgument(emptyAudioPath);
        executor.addArgument("-filter_complex");
        executor.addArgument("[0:0] [1:0] concat=n=2:v=0:a=1 [a]");
        executor.addArgument("-map");
        executor.addArgument("[a]");
        executor.addArgument("-ar");
        executor.addArgument("48000");
//        executor.addArgument("-t");
//        executor.addArgument("10");
        executor.addArgument("-y");
        executor.addArgument(loop);
        BufferedReader br = null;
        try {
            executor.execute();
            br = new BufferedReader(new InputStreamReader(executor.getErrorStream()));
            String line;
            while ((line = br.readLine()) != null) {
                //输出处理过程中的日志（辅助观察处理过程）
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("水印连接「{}秒」间隔音频完成！", seconds);
    }

    @Test
    public void updateAudio() throws EncoderException {
        File sourceFile = new File("D:\\KuGou\\watermark.mp3");

        MultimediaObject srcMultiObj = new MultimediaObject(sourceFile);
        long duration = srcMultiObj.getInfo().getDuration();
        srcMultiObj.getInfo().setDuration(duration * 10);

        log.info("水印文件播放时长：{}ms", duration);

        File targetFile = new File("D:\\KuGou\\watermartPlus.mp3");

        AudioAttributes audioAttributes = new AudioAttributes();
        Encoder encoder = new Encoder();
        EncodingAttributes encodingAttributes = new EncodingAttributes();


        audioAttributes.setVolume(100);
        audioAttributes.setCodec("libmp3lame");

//        encodingAttributes.setInputFormat("mp3");
        encodingAttributes.setOutputFormat("mp3");
        encodingAttributes.setAudioAttributes(audioAttributes);
        encodingAttributes.setDuration(duration * 10f);

        encoder.encode(srcMultiObj, targetFile, encodingAttributes);

    }

    /**
     * 添加音频水印
     *
     * @throws EncoderException
     */
    @Test
    void generateWater() throws EncoderException {
        DefaultFFMPEGLocator ffmpegLocator = new DefaultFFMPEGLocator();
        ProcessWrapper executor = ffmpegLocator.createExecutor();

        MultimediaObject multimediaObject = new MultimediaObject(sourceFile);
        long time = multimediaObject.getInfo().getDuration() / 1_000;
        log.info("Time: " + time);
        executor.addArgument("-i");
        executor.addArgument(sourceFile.getAbsolutePath());
        executor.addArgument("-stream_loop");
        executor.addArgument("2");
        executor.addArgument("-i");
        executor.addArgument(waterMark.getAbsolutePath());
        executor.addArgument("-filter_complex");
        executor.addArgument("[1:a][0:a]amix");
        executor.addArgument("-t");
        executor.addArgument(time + "");
        executor.addArgument("-ar");
        executor.addArgument("48000");
        executor.addArgument("-f");
        executor.addArgument("mp3");
        executor.addArgument("-y");
        executor.addArgument(resultFile.getAbsolutePath());

        BufferedReader br = null;
        try {
            executor.execute();
            br = new BufferedReader(new InputStreamReader(executor.getErrorStream()));
            String line;
            while ((line = br.readLine()) != null) {
                //输出处理过程中的日志（辅助观察处理过程）
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
