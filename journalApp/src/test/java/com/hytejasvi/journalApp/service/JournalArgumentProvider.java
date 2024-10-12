package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class JournalArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(Arguments.of(JournalEntry.builder().title("Title1").content("Content1")),
                Arguments.of(JournalEntry.builder().title("")));
    }
}
