package com.company;

import org.apache.http.Header;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicHeader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteArrayEntity extends AbstractHttpEntity implements Cloneable
{
    private final byte[] data;
    private final int offset;
    private final int length;

    public ByteArrayEntity(final byte[] data, final int offset, final int length) {
        super();
        if ((offset < 0) || (offset > data.length) || (length < 0) ||
                ((offset + length) < 0) || ((offset + length) > data.length)) {
            throw new IndexOutOfBoundsException("off: " + offset + " len: " + length + " b.length: " + data.length);
        }
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    @Override
    public boolean isRepeatable()
    {
        return true;
    }

    @Override
    public long getContentLength()
    {
        return this.length;
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException
    {
        return new ByteArrayInputStream(this.data, this.offset, this.length);
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException
    {
        outputStream.write(this.data, this.offset, this.length);
        outputStream.flush();
    }

    @Override
    public Header getContentType()
    {
        return new BasicHeader("Content-Type", "application/octet-stream");
    }

    @Override
    public boolean isStreaming()
    {
        return false;
    }

}


