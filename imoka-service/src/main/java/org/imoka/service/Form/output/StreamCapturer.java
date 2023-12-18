/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.Form.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author r.hendrick
 */
public class StreamCapturer extends OutputStream {

    private StringBuilder buffer;
    private String prefix;
    private Consumer consumer;
    private PrintStream old;

    public StreamCapturer(String prefix, Consumer consumer, PrintStream old) {
        this.prefix = prefix;
        buffer = new StringBuilder(128);
        buffer.append(prefix).append(" >> ");
        this.old = old;
        this.consumer = consumer;
    }

    @Override
    public void write(int b) throws IOException {
        char c = (char) b;
        String value = Character.toString(c);
        buffer.append(value);
        if (value.equals("\n")) {
            consumer.appendText(buffer.toString());
            buffer.delete(0, buffer.length());
            buffer.append(prefix).append(" >> ");
        }
        old.print(c);
    }
}
