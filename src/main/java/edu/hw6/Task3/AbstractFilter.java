package edu.hw6.Task3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    default AbstractFilter and(AbstractFilter other) {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return AbstractFilter.this.accept(entry) && other.accept(entry);
            }
        };
    }

    static AbstractFilter attributes(boolean readable, boolean writable) {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return Files.isReadable(entry) == readable
                    && Files.isWritable(entry) == writable;
            }
        };
    }

    static AbstractFilter largeThan(int size) {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return Files.size(entry) > size;
            }
        };
    }

    static AbstractFilter globMatches(String str) {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return entry.toFile().toString().endsWith(str);
            }
        };
    }

    static AbstractFilter regexContains(String regex) {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {

                return entry.toFile().getName().matches(regex);
            }
        };
    }

    @SuppressWarnings("MagicNumber")
    static AbstractFilter magicNumber(byte[] bytes) {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {
                try (SeekableByteChannel channel = Files.newByteChannel(entry)) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
                    int countAvailableBytesForRead = channel.read(byteBuffer);
                    if (bytes.length != countAvailableBytesForRead) {
                        return false;
                    }

                    byteBuffer.flip();
                    for (int aByte : bytes) {
                        if ((byteBuffer.get() & 0xff) != aByte) {
                            return false;
                        }
                    }
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        };
    }
}
