package com.example.pattern.builder;


public abstract class MacbookProBuilder {

    protected MacbookPro macbookPro;

    public MacbookProBuilder(){
        //create object first
        macbookPro = new MacbookPro();
    }

    public MacbookPro build(){
        return this.macbookPro;
    }

    //define build method for product
    abstract public MacbookProBuilder buildCPU(MacbookPro.Processor processor);

    abstract public MacbookProBuilder buildMemory(MacbookPro.Memory memory);

    abstract public MacbookProBuilder buildGraphics(MacbookPro.Graphics graphics);

    abstract public MacbookProBuilder buildStore(MacbookPro.Storage storage);

    abstract public MacbookProBuilder buildKeyboard(MacbookPro.Keyboard keyboard);
}
