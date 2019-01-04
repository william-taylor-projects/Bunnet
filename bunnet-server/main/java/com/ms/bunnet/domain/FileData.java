package com.ms.bunnet.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileData {
    private List<String> columns;
    private List<String> lines;

    public FileData(byte[] bytes) {
        lines = Arrays.asList(new String(bytes).split(System.lineSeparator()));
        columns = Collections.emptyList();
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
