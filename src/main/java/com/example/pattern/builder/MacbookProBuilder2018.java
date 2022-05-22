package com.example.pattern.builder;

//concrete builder
public class MacbookProBuilder2018 extends MacbookProBuilder{

    @Override
    public  MacbookProBuilder buildCPU(MacbookPro.Processor processor) {
        macbookPro.setProcessor(processor);
        return this;
    }

    @Override
    public MacbookProBuilder buildMemory(MacbookPro.Memory memory) {
        macbookPro.setMemory(memory);
        return this;
    }

    @Override
    public MacbookProBuilder buildGraphics(MacbookPro.Graphics graphics) {
        macbookPro.setGraphics(graphics);
        return this;
    }

    @Override
    public MacbookProBuilder buildStore(MacbookPro.Storage storage) {
        macbookPro.setStorage(storage);
        return this;
    }

    @Override
    public MacbookProBuilder buildKeyboard(MacbookPro.Keyboard keyboard) {
        macbookPro.setKeyboard(keyboard);
        return this;
    }
}
