/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.rf.ide.core.testdata.text.read;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.rf.ide.core.testdata.model.IChildElement;
import org.rf.ide.core.testdata.model.RobotFile;
import org.rf.ide.core.testdata.text.read.LineReader.Constant;
import org.rf.ide.core.testdata.text.read.recognizer.RobotToken;
import org.rf.ide.core.testdata.text.read.separators.Separator;
import org.rf.ide.core.testdata.text.read.separators.Separator.SeparatorType;

public class RobotLine implements IChildElement<RobotFile> {

    private final RobotFile parent;

    private final int lineNumber;

    private List<IRobotLineElement> lineElements = new ArrayList<>(0);

    private Optional<SeparatorType> separatorForLine = Optional.empty();

    private IRobotLineElement eol = EndOfLineBuilder.newInstance()
            .setEndOfLines(null)
            .setLineNumber(IRobotLineElement.NOT_SET)
            .setStartColumn(IRobotLineElement.NOT_SET)
            .setStartOffset(IRobotLineElement.NOT_SET)
            .buildEOL();

    public RobotLine(final int lineNumber, final RobotFile parent) {
        this.lineNumber = lineNumber;
        this.parent = parent;
    }

    public void setSeparatorType(final SeparatorType separatorForLine) {
        this.separatorForLine = Optional.ofNullable(separatorForLine);
    }

    public Optional<SeparatorType> getSeparatorForLine() {
        return separatorForLine;
    }

    @Override
    public RobotFile getParent() {
        return parent;
    }

    public List<IRobotLineElement> getLineElements() {
        return lineElements;
    }

    public Stream<IRobotLineElement> elementsStream() {
        return lineElements.stream();
    }

    public List<RobotToken> getLineTokens() {
        return tokensStream().collect(Collectors.toList());
    }

    public Stream<RobotToken> tokensStream() {
        return lineElements.stream().filter(RobotToken.class::isInstance).map(RobotToken.class::cast);
    }

    public void setLineElements(final List<IRobotLineElement> lineElements) {
        this.lineElements = lineElements;
    }

    public void addLineElement(final IRobotLineElement lineElement) {
        lineElements.add(lineElement);
    }

    public void addLineElementAt(final int position, final IRobotLineElement lineElement) {
        lineElements.add(position, lineElement);
    }

    public void setLineElementAt(final int position, final IRobotLineElement lineElement) {
        lineElements.set(position, lineElement);
    }

    public boolean isEmpty() {
        return elementsStream().map(IRobotLineElement::getText).allMatch(s -> s.trim().isEmpty())
                || elementsStream().allMatch(s -> s instanceof Separator);
    }

    public Optional<Integer> getElementPositionInLine(final int offset, final PositionCheck posCheckStrategy) {
        for (int i = 0; i < lineElements.size(); i++) {
            final IRobotLineElement e = lineElements.get(i);
            if (posCheckStrategy.meets(e, offset)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public Optional<Integer> getElementPositionInLine(final IRobotLineElement elem) {
        for (int i = 0; i < lineElements.size(); i++) {
            if (lineElements.get(i) == elem) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public IRobotLineElement getEndOfLine() {
        return eol;
    }

    public void setEndOfLine(final List<Constant> endOfLine, final int currentOffset, final int currentColumn) {
        this.eol = EndOfLineBuilder.newInstance()
                .setEndOfLines(endOfLine)
                .setStartColumn(currentColumn)
                .setStartOffset(currentOffset)
                .setLineNumber(lineNumber)
                .buildEOL();
    }

    @Override
    public String toString() {
        return String.format("RobotLine [lineNumber=%s, lineElements=%s, endOfLine=%s]", lineNumber, lineElements, eol);
    }

    public RobotLine deepCopy() {
        final RobotLine copy = new RobotLine(this.getLineNumber(), this.getParent());
        for (final IRobotLineElement elem : this.getLineElements()) {
            copy.addLineElement(elem.copy());
        }

        final IRobotLineElement endOfLine = this.getEndOfLine();
        if (endOfLine != null) {
            copy.setEndOfLine(LineReader.Constant.get(endOfLine), endOfLine.getStartOffset(),
                    endOfLine.getStartColumn());
        }
        return copy;
    }

    public static enum PositionCheck {
        STARTS {

            @Override
            public boolean meets(final IRobotLineElement element, final int offset) {
                return element.getFilePosition().getOffset() == offset;
            }
        },
        INSIDE {

            @Override
            public boolean meets(final IRobotLineElement element, final int offset) {
                return element.getStartOffset() >= offset
                        && offset <= element.getStartOffset() + element.getEndColumn() - element.getStartColumn();
            }
        },
        ENDS {

            @Override
            public boolean meets(final IRobotLineElement element, final int offset) {
                return element.getStartOffset() + element.getEndColumn() - element.getStartColumn() == offset;
            }
        };

        public abstract boolean meets(final IRobotLineElement element, final int offset);
    }
}
