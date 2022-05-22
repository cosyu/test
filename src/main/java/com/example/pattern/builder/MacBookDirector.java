package com.example.pattern.builder;

// Director is to control the build process
public class MacBookDirector {

    private MacbookProBuilder macbookProBuilder;

    public MacBookDirector(MacbookProBuilder macbookProBuilder){
        this.macbookProBuilder = macbookProBuilder;
    }

    public MacbookPro lowSpec(){
        return macbookProBuilder
                .buildCPU(new MacbookPro.Processor("2.2GHz 6 核心第八代 Intel Core i7 處理器"))
                .buildMemory(new MacbookPro.Memory(16))
                .buildGraphics(new MacbookPro.Graphics("Radeon Pro 555X 配備 4GB GDDR5 記憶體"))
                .buildStore(new MacbookPro.Storage(256))
                .buildKeyboard(new MacbookPro.Keyboard("中文注音"))
                .build();
    }

    public MacbookPro highSpec(){
        return macbookProBuilder
                .buildCPU(new MacbookPro.Processor("2.6GHz 6 核心第八代 Intel Core i7 處理器"))
                .buildMemory(new MacbookPro.Memory(16))
                .buildGraphics(new MacbookPro.Graphics("Radeon Pro 560X 配備 4GB GDDR5 記憶體"))
                .buildStore(new MacbookPro.Storage(512))
                .buildKeyboard(new MacbookPro.Keyboard("中文注音"))
                .build();
    }
}
