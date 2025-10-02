package com.unifun.ivr;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class IvrScript extends BaseAgiScript {

    @Override
    public void service(AgiRequest request, AgiChannel channel) throws AgiException {
        final String RECORDING_PATH = "/tmp/user_message";
        try {
            channel.answer();
            String digitString = channel.getData("custom/ivr-main-menu", 10000, 1);
            System.out.println("Received: "+digitString);
            if (digitString != null && !digitString.isEmpty()) {
                char digit = digitString.charAt(0);

                switch (digit) {
                    case '1':
                        handleOption1(channel);
                        break;
                    case '2':
                        handleOption2(channel);
                        break;
                    case '3':
                        handleOption3(channel, RECORDING_PATH);
                        break;
                    default:
                        break;
                }
            }

        } catch (AgiException e) {
            System.err.println("Error in simple IVR script: " + e.getMessage());
        } finally {
            channel.hangup();
        }
    }

    private void handleOption1(AgiChannel channel) throws AgiException {
        channel.streamFile("custom/you-smiled");
    }

    private void handleOption2(AgiChannel channel) throws AgiException {
        String pressedDigitString = channel.getData("custom/press-any-digit", 5000, 1);

        if (pressedDigitString != null && !pressedDigitString.isEmpty()) {
            channel.streamFile("custom/you-pressed");
            channel.sayDigits(pressedDigitString);
        }
    }

    private void handleOption3(AgiChannel channel, String recordingPath) throws AgiException {
        channel.streamFile("custom/record-prompt");
        channel.recordFile(recordingPath, "wav", "#", 60000, 0, true, 0);
        channel.streamFile("custom/your-message-is");
        channel.streamFile(recordingPath);
    }
}
